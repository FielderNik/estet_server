package com.estet.features.questions.queries

import com.estet.database.Query
import com.estet.features.questions.QuestionConstants

class UpdateQuestionQuery : Query {
    override fun getQuery(): String {
        return """
            UPDATE ${QuestionConstants.TABLE}
            SET 
                ${QuestionConstants.QUESTION} = ?, 
                ${QuestionConstants.LEVEL} = ?, 
                ${QuestionConstants.ART_TYPE} = ?, 
                ${QuestionConstants.SCORE} = ?
            WHERE ${QuestionConstants.ID} = ?
        """.trimIndent()
    }
}