package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class CreateUserQuery : Query {
    override fun getQuery(): String {
        return """
            INSERT INTO ${UserConstants.TABLE} 
            (
            ${UserConstants.ID}, 
            ${UserConstants.NAME}, 
            ${UserConstants.EMAIL}, 
            ${UserConstants.AVATAR_URL},
            ${UserConstants.PHONE},
            ${UserConstants.AGE}
            ) 
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()
    }
}