package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class CreateUserQuery : Query {
    override fun getQuery(): String {
        val query = """
            INSERT INTO ${UserConstants.TABLE} 
            (${UserConstants.ID}, ${UserConstants.NAME}, ${UserConstants.EMAIL}, ${UserConstants.AVATAR_URL}) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        println(query)
        return query
    }
}