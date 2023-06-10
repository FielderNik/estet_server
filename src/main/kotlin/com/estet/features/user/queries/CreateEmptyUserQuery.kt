package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class CreateEmptyUserQuery : Query {
    override fun getQuery(): String {
        return """
            INSERT INTO ${UserConstants.TABLE} 
            (${UserConstants.ID}) VALUES (?)
        """.trimIndent()
    }
}