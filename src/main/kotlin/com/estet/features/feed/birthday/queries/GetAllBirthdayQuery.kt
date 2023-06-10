package com.estet.features.feed.birthday.queries

import com.estet.database.Query
import com.estet.features.feed.birthday.BirthdayConstants

class GetAllBirthdayQuery: Query {
    override fun getQuery(): String {
        return """
            SELECT *
             FROM ${BirthdayConstants.TABLE}
        """.trimIndent()
    }
}