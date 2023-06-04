package com.estet.features.feed

import com.estet.database.BaseService
import com.estet.features.feed.birthday.Birthday
import com.estet.features.feed.birthday.BirthdayConstants
import com.estet.features.feed.birthday.queries.GetAllBirthdayQuery
import com.estet.features.feed.news.News
import com.estet.features.feed.news.News.NewsCategory.INTERVIEW
import com.estet.features.feed.news.NewsConstants
import com.estet.features.feed.news.queries.GetAllNewsQuery
import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import io.ktor.util.*
import java.sql.Connection
import java.sql.ResultSet

typealias NewsList = List<FeedItem.NewsItem>
typealias InterviewList = FeedItem.InterviewsItem

class FeedService(private val connection: Connection) : BaseService() {

    suspend fun getAll(): Either<Failure, List<FeedItem>> {
        return handleRequest {

            val (newsList, interviewList) = getFeedNewsItemsList()
            val birthdayList = getFeedBirthdayItemsList()

            FeedContentConstructor(newsList, interviewList, birthdayList).createFeed()
        }
    }

    private fun getFeedBirthdayItemsList(): FeedItem.BirthdaysItem {
        val resultList: MutableList<FeedItem.BirthdaysItem.BirthdayItem> = mutableListOf()
        val statement = connection.prepareStatement(GetAllBirthdayQuery().getQuery())
        val resultSet = statement.executeQuery()
        while (resultSet.next()) {
            val birthday = getBirthday(resultSet)
            resultList.add(birthday)
        }
        return FeedItem.BirthdaysItem(resultList)
    }


    private fun getFeedNewsItemsList(): Pair<NewsList, InterviewList> {
        val newsList: MutableList<FeedItem.NewsItem> = mutableListOf()
        val interviewsList: MutableList<News> = mutableListOf()
        val statement = connection.prepareStatement(GetAllNewsQuery().getQuery())
        val resultSet = statement.executeQuery()

        while (resultSet.next()) {
            val news = getNews(resultSet)
            if (news.category == INTERVIEW) {
                interviewsList.add(news)
            } else {
                newsList.add(FeedItem.NewsItem(news))
            }
        }

        return Pair(newsList, FeedItem.InterviewsItem(interviewsList))
    }

    private fun getNews(resultSet: ResultSet): News {
        val category =
            News.NewsCategory.valueOf(resultSet.getString(NewsConstants.CATEGORY).toUpperCasePreservingASCIIRules())

        return News(
            id = resultSet.getString(NewsConstants.ID),
            title = resultSet.getString(NewsConstants.TITLE),
            subtitle = resultSet.getString(NewsConstants.SUBTITLE),
            date = resultSet.getString(NewsConstants.DATE),
            category = category,
            imageUrl = resultSet.getString(NewsConstants.IMAGE_URL),
            url = resultSet.getString(NewsConstants.URL),
        )
    }

    private fun getBirthday(resultSet: ResultSet): FeedItem.BirthdaysItem.BirthdayItem {
        val birthdayId = resultSet.getString(BirthdayConstants.ID)
        val name = resultSet.getString(BirthdayConstants.NAME)
        val image = resultSet.getString(BirthdayConstants.IMAGE)
        return Birthday(id = birthdayId, name = name, image = image).toFeedBirthdayItem()
    }

}