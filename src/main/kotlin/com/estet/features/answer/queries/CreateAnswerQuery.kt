package com.estet.features.answer.queries

import com.estet.database.Query
import com.estet.features.answer.AnswerConstants

internal class CreateAnswerQuery : Query {
    override fun getQuery(): String {
        return """
            INSERT INTO ${AnswerConstants.TABLE} 
            (
             ${AnswerConstants.ID}, 
             ${AnswerConstants.QUESTION_ID},
             ${AnswerConstants.ANSWER}, 
             ${AnswerConstants.IS_CORRECT}
            ) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()
    }
}