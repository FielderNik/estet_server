package com.estet.features.schools

import com.estet.database.BaseService
import com.estet.features.schools.queries.GetAllSchoolsQuery
import com.estet.features.schools.queries.GetSchoolByIdQuery
import com.estet.features.schools.School.GeoPoint
import com.estet.utils.funcional.*
import java.sql.Connection
import java.sql.ResultSet

class SchoolService(private val connection: Connection) : BaseService() {

    suspend fun getById(id: String): Either<Failure, School> {
        return handleRequest {
            val statement = connection.prepareStatement(GetSchoolByIdQuery().getQuery())
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getSchool(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<School>> {
        return handleRequest {
            val resultList: MutableList<School> = mutableListOf()
            val statement = connection.prepareStatement(GetAllSchoolsQuery().getQuery())
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val news = getSchool(resultSet)
                resultList.add(news)
            }
            resultList
        }
    }

    private fun getSchool(resultSet: ResultSet): School {
        val point = try {
            val pointDto = resultSet.getArray(SchoolConstants.POINT).array as Array<Double>
            GeoPoint(pointDto[0], pointDto[1])
        } catch (e: Exception) {
            println(e.message)
            GeoPoint.EMPTY
        }

        return School(
            id = resultSet.getString(SchoolConstants.ID),
            title = resultSet.getString(SchoolConstants.TITLE),
            geoPoint = point,
            openingHours = resultSet.getString(SchoolConstants.OPENING_HOURS),
        )
    }
}