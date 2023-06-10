package com.estet.database.sharedqueries

import com.estet.database.Query

class GetByIdQuery(
    private val table: String,
    private val id: String,
) : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM $table
            WHERE $id = ?
        """.trimIndent()
    }
}