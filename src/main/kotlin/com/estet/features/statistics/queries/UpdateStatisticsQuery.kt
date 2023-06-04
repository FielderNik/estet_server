package com.estet.features.statistics.queries

import com.estet.database.Query
import com.estet.features.statistics.StatisticsConstants

class UpdateStatisticsQuery : Query {
    override fun getQuery(): String {
        return """
            UPDATE ${StatisticsConstants.TABLE}
            SET 
                ${StatisticsConstants.USER_ID} = ?, 
                ${StatisticsConstants.QUESTION_ID} = ?, 
                ${StatisticsConstants.SELECTED_ANSWER_ID} = ?
            WHERE ${StatisticsConstants.ID} = ?
        """.trimIndent()
    }
}