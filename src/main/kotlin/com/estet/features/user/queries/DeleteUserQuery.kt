package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class DeleteUserQuery : Query {
    override fun getQuery(): String {
        return """
            DELETE
            FROM ${UserConstants.TABLE}
            WHERE ${UserConstants.ID} = ?
        """.trimIndent()
    }
}