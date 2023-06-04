package com.estet.features.statistics.queries

import com.estet.database.Query
import com.estet.features.statistics.StatisticsConstants

class CreateStatisticsQuery : Query {
    override fun getQuery(): String {
        return """
            INSERT INTO ${StatisticsConstants.TABLE} 
            (
             ${StatisticsConstants.ID}, 
             ${StatisticsConstants.USER_ID},
             ${StatisticsConstants.QUESTION_ID}, 
             ${StatisticsConstants.SELECTED_ANSWER_ID}
            ) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()
    }
}