package com.estet.database.sharedqueries

import com.estet.database.Query

class DeleteQuery(
    private val table: String,
    private val id: String
) : Query {
    override fun getQuery(): String {
        return """
            DELETE
            FROM $table
            WHERE $id = ?
        """.trimIndent()
    }
}