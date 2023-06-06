package com.estet.features.schools.queries

import com.estet.database.Query
import com.estet.features.schools.SchoolConstants

class GetSchoolByIdQuery: Query {
    override fun getQuery(): String {
        return """
            SELECT *
            FROM ${SchoolConstants.TABLE}
            WHERE id = ?
        """.trimIndent()
    }
}