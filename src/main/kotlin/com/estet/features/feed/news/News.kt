package com.estet.features.feed.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("subtitle")
    val subtitle: String,
    @SerialName("date")
    val date: String,
    @SerialName("category")
    val category: NewsCategory,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("url")
    val url: String,
) {
    enum class NewsCategory {
        @SerialName("dance")
        DANCE,
        @SerialName("painting")
        PAINTING,
        @SerialName("theatre")
        THEATRE,
        @SerialName("music")
        MUSIC,
        @SerialName("seminars")
        SEMINARS,
        @SerialName("interview")
        INTERVIEW,
        @SerialName("grants")
        GRANTS;
    }
}