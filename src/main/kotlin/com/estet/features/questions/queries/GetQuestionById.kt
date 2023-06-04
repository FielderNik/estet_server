package com.estet.features.questions.queries

import com.estet.database.Query
import com.estet.features.questions.QuestionConstants

class GetQuestionById : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${QuestionConstants.TABLE}
            WHERE ${QuestionConstants.ID} = ?
        """.trimIndent()
    }
}