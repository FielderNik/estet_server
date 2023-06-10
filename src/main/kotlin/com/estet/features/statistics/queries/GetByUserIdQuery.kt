package com.estet.features.statistics.queries

import com.estet.database.Query
import com.estet.features.statistics.StatisticsConstants

class GetByUserIdQuery : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${StatisticsConstants.TABLE}
            WHERE ${StatisticsConstants.USER_ID} = ?
        """.trimIndent()
    }
}