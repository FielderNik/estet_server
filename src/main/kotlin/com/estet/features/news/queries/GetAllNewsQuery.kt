package com.estet.features.news.queries

import com.estet.database.Query
import com.estet.features.news.NewsConstants

class GetAllNewsQuery: Query {
    override fun getQuery(): String {
        return """
            SELECT *
             FROM ${NewsConstants.TABLE}
        """.trimIndent()
    }
}