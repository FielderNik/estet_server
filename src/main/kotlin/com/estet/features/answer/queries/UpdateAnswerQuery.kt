package com.estet.features.answer.queries

import com.estet.database.Query
import com.estet.features.answer.AnswerConstants

class UpdateAnswerQuery : Query {
    override fun getQuery(): String {
        return """
            UPDATE ${AnswerConstants.TABLE}
            SET 
                ${AnswerConstants.QUESTION_ID} = ?, 
                ${AnswerConstants.ANSWER} = ?, 
                ${AnswerConstants.IS_CORRECT} = ?
            WHERE ${AnswerConstants.ID} = ?
        """.trimIndent()
    }
}