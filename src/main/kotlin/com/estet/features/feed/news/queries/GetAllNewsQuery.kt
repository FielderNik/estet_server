package com.estet.features.feed.news.queries

import com.estet.database.Query
import com.estet.features.feed.news.NewsConstants

class GetAllNewsQuery: Query {
    override fun getQuery(): String {
        return """
            SELECT *
             FROM ${NewsConstants.TABLE}
        """.trimIndent()
    }
}