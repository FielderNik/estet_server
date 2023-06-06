package com.estet.features.schools.queries

import com.estet.database.Query
import com.estet.features.schools.SchoolConstants

class GetAllSchoolsQuery : Query {
    override fun getQuery(): String {
        return """
            SELECT *
             FROM ${SchoolConstants.TABLE}
        """.trimIndent()
    }
}