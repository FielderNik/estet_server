package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class GetUserByIdQuery : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${UserConstants.TABLE}
            WHERE ${UserConstants.ID} = ?
        """.trimIndent()
    }
}