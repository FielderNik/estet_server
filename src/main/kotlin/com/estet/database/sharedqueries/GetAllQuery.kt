package com.estet.database.sharedqueries

import com.estet.database.Query

class GetAllQuery(private val table: String) : Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM $table
        """.trimIndent()
    }
}