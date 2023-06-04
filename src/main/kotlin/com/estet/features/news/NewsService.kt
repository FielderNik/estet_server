package com.estet.features.news

import com.estet.database.BaseService
import com.estet.features.news.queries.GetAllNewsQuery
import com.estet.features.news.queries.GetNewsById
import com.estet.utils.funcional.*
import io.ktor.util.*
import java.sql.Connection
import java.sql.ResultSet

class NewsService(private val connection: Connection) : BaseService() {

    suspend fun getById(id: String): Either<Failure, News> {
        return handleRequest {
            val statement = connection.prepareStatement(GetNewsById().getQuery())
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getNews(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<News>> {
        return handleRequest {
            val resultList: MutableList<News> = mutableListOf()
            val statement = connection.prepareStatement(GetAllNewsQuery().getQuery())
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val news = getNews(resultSet)
                resultList.add(news)
            }
            resultList
        }
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
}