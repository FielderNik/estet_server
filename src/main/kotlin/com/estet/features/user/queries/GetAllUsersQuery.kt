package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class GetAllUsersQuery : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${UserConstants.TABLE}
        """.trimIndent()
    }
}