package com.estet.features.feed

import com.estet.features.feed.birthday.Birthday
import com.estet.features.feed.news.News
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class FeedItem {

    @Serializable
    @SerialName(TYPE_NEWS)
    data class NewsItem(
        @SerialName("content")
        val news: News,
    ) : FeedItem()

    @Serializable
    @SerialName(TYPE_BIRTHDAYS)
    data class BirthdaysItem(
        @SerialName("content")
        val birthdayList: List<BirthdayItem>
    ) : FeedItem() {

        @Serializable
        data class BirthdayItem(
            @SerialName("id")
            val id: String,
            @SerialName("name")
            val name: String,
            @SerialName("image")
            val image: String?
        )
    }

    @Serializable
    @SerialName(TYPE_INTERVIEWS)
    data class InterviewsItem(
        @SerialName("content")
        val birthdayList: List<News>
    ) : FeedItem()

    companion object {
        const val TYPE_NEWS = "news"
        const val TYPE_BIRTHDAYS = "birthdays"
        const val TYPE_INTERVIEWS = "interviews"
    }
}


fun Birthday.toFeedBirthdayItem(): FeedItem.BirthdaysItem.BirthdayItem {
    return FeedItem.BirthdaysItem.BirthdayItem(
        id = id,
        name = name,
        image = image
    )
}