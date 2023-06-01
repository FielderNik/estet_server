package com.estet.features.birthday

import com.estet.database.BaseService
import com.estet.utils.funcional.*
import com.estet.utils.generateId
import java.sql.Connection
import java.sql.ResultSet

class BirthdayService(private val connection: Connection) : BaseService() {

    companion object {
        private const val INSERT_BIRTHDAY = "INSERT INTO birthday (id, name, image) VALUES (?, ?, ?)"
        private const val SELECT_BIRTHDAY_BY_ID = "SELECT id, name, image FROM birthday WHERE id = ?"
        private const val UPDATE_BIRTHDAY = "UPDATE birthday SET name = ?, image = ? WHERE id = ?"
        private const val DELETE_BIRTHDAY = "DELETE FROM birthday WHERE id = ?"
        private const val SELECT_ALL = "SELECT id, name, image FROM birthday;"
    }

    suspend fun create(name: String, imageUrl: String): Either<Failure, String> {
        return handleRequest {
            val statement = connection.prepareStatement(INSERT_BIRTHDAY/*, Statement.RETURN_GENERATED_KEYS*/)
            val id = generateId()
            statement.setString(1, id)
            statement.setString(2, name)
            statement.setString(3, imageUrl)
            statement.executeUpdate()
            id
        }
    }

    // Read a city
    suspend fun getById(id: String): Either<Failure, Birthday> {
        return handleRequest {
            val statement = connection.prepareStatement(SELECT_BIRTHDAY_BY_ID)
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getBirthday(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<Birthday>> {
        return handleRequest {
            val resultList: MutableList<Birthday> = mutableListOf()
            val statement = connection.prepareStatement(SELECT_ALL)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val birthday = getBirthday(resultSet)
                resultList.add(birthday)
            }
            resultList
        }

    }

    suspend fun update(id: String, birthday: Birthday): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(UPDATE_BIRTHDAY)
            statement.setString(1, birthday.name)
            statement.setString(2, birthday.image)
            statement.setString(3, id)
            statement.executeUpdate()
            None
        }
    }

    suspend fun delete(id: String): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(DELETE_BIRTHDAY)
            statement.setString(1, id)
            statement.executeUpdate()
            None
        }

    }

    private fun getBirthday(resultSet: ResultSet): Birthday {
        val birthdayId = resultSet.getString(BirthdayConstants.ID)
        val name = resultSet.getString(BirthdayConstants.NAME)
        val image = resultSet.getString(BirthdayConstants.IMAGE)
        return Birthday(id = birthdayId, name = name, image = image)
    }
}