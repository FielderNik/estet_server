package com.estet.features.feed

class FeedContentConstructor(
    private val newsList: List<FeedItem.NewsItem>,
    private val interviewsItem: FeedItem.InterviewsItem,
    private val birthdaysItem: FeedItem.BirthdaysItem,
) {
    fun createFeed(): List<FeedItem> {
        val result = mutableListOf<FeedItem>()
        if (newsList.isEmpty()) {
            return result
        }
        if (newsList.size == 1) {
            result.add(newsList.first())
        }
        if (newsList.size > 1) {
            result.addAll(newsList.subList(0, 2))
        }
        if (birthdaysItem.birthdayList.isNotEmpty()) {
            result.add(birthdaysItem)
        }
        if (newsList.size > 3) {
            result.addAll(newsList.subList(2, 3))
        }
        if (interviewsItem.birthdayList.isNotEmpty()) {
            result.add(interviewsItem)
        }
        if (newsList.size > 4) {
            result.addAll(newsList.subList(3, newsList.lastIndex))
        }
        return result
    }
}