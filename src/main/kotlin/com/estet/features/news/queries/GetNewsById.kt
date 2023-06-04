package com.estet.features.news.queries

import com.estet.database.Query
import com.estet.features.news.NewsConstants

class GetNewsById : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${NewsConstants.TABLE}
            WHERE id = ?
        """.trimIndent()
    }
}