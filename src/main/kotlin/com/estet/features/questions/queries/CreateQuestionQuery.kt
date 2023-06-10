package com.estet.features.questions.queries

import com.estet.database.Query
import com.estet.features.questions.QuestionConstants
import com.estet.features.user.UserConstants

internal class CreateQuestionQuery : Query {
    override fun getQuery(): String {
        return """
            INSERT INTO ${QuestionConstants.TABLE} 
            (
             ${QuestionConstants.ID}, 
             ${QuestionConstants.QUESTION},
             ${QuestionConstants.LEVEL}, 
             ${QuestionConstants.ART_TYPE},
             ${QuestionConstants.SCORE},
             ${QuestionConstants.DESCRIPTION}
            ) 
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()
    }
}