package com.estet.features.user

import com.estet.database.BaseService
import com.estet.features.user.queries.*
import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import com.estet.utils.funcional.None
import com.estet.utils.generateId
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types

class UserService(private val connection: Connection) : BaseService() {

    suspend fun create(
        name: String?,
        email: String?,
        avatarUrl: String?,
        phone: String?,
        age: Int?,
    ): Either<Failure, String> {
        return handleRequest {
            val statement = connection.prepareStatement(CreateUserQuery().getQuery())
            val id = generateId()
            statement.setString(1, id)
            statement.setString(2, name)
            statement.setString(3, email)
            statement.setString(4, avatarUrl)
            statement.setString(5, phone)
            if (age == null) {
                statement.setNull(6, Types.NUMERIC)
            } else {
                statement.setInt(6, age)
            }
            statement.executeUpdate()
            id
        }
    }

    suspend fun getById(id: String): Either<Failure, User> {
        return handleRequest {
            val statement = connection.prepareStatement(GetUserByIdQuery().getQuery())
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getUser(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<User>> {
        return handleRequest {
            val resultList: MutableList<User> = mutableListOf()
            val statement = connection.prepareStatement(GetAllUsersQuery().getQuery())
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val user = getUser(resultSet)
                resultList.add(user)
            }
            resultList
        }

    }

    suspend fun update(id: String, user: User): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(UpdateUserQuery().getQuery())
            statement.setString(1, user.name)
            statement.setString(2, user.email)
            statement.setString(3, user.avatarUrl)
            statement.setString(4, id)
            statement.executeUpdate()
            None
        }
    }

    suspend fun delete(id: String): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(DeleteUserQuery().getQuery())
            statement.setString(1, id)
            statement.executeUpdate()
            None
        }

    }

    private fun getUser(resultSet: ResultSet): User {
        val userId = resultSet.getString(UserConstants.ID)
        val name = resultSet.getString(UserConstants.NAME)
        val email = resultSet.getString(UserConstants.EMAIL)
        val avatarUrl = resultSet.getString(UserConstants.AVATAR_URL)
        return User(id = userId, name = name, email, avatarUrl)
    }
}