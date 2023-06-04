package com.estet.features.user.queries

import com.estet.database.Query
import com.estet.features.user.UserConstants

class UpdateUserQuery : Query {
    override fun getQuery(): String {
        return """
            UPDATE ${UserConstants.TABLE}
            SET ${UserConstants.NAME} = ?, ${UserConstants.EMAIL} = ?, ${UserConstants.AVATAR_URL} = ?
            WHERE ${UserConstants.ID} = ?
        """.trimIndent()
    }
}