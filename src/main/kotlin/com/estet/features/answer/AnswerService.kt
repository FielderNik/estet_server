package com.estet.features.answer

import com.estet.database.BaseService
import com.estet.database.sharedqueries.DeleteQuery
import com.estet.database.sharedqueries.GetAllQuery
import com.estet.database.sharedqueries.GetByIdQuery
import com.estet.features.answer.queries.CreateAnswerQuery
import com.estet.features.answer.queries.UpdateAnswerQuery
import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import com.estet.utils.funcional.None
import com.estet.utils.generateId
import java.sql.Connection
import java.sql.ResultSet

class AnswerService(private val connection: Connection) : BaseService() {

    suspend fun create( questionId: String, answer: String, isCorrect: Boolean): Either<Failure, String> {
        return handleRequest {
            val statement = connection.prepareStatement(CreateAnswerQuery().getQuery())
            val id = generateId()
            statement.setString(1, id)
            statement.setString(2, questionId)
            statement.setString(3, answer)
            statement.setBoolean(4, isCorrect)
            statement.executeUpdate()
            id
        }
    }

    suspend fun getById(id: String): Either<Failure, Answer> {
        return handleRequest {
            val query = GetByIdQuery(AnswerConstants.TABLE, AnswerConstants.ID).getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getAnswer(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<Answer>> {
        return handleRequest {
            val resultList: MutableList<Answer> = mutableListOf()
            val query = GetAllQuery(AnswerConstants.TABLE).getQuery()
            val statement = connection.prepareStatement(query)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val answer = getAnswer(resultSet)
                resultList.add(answer)
            }
            resultList
        }

    }

    suspend fun update(id: String, answer: Answer): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(UpdateAnswerQuery().getQuery())
            statement.setString(1, answer.questionId)
            statement.setString(2, answer.answer)
            statement.setBoolean(3, answer.isCorrect)
            statement.setString(4, id)
            statement.executeUpdate()
            None
        }
    }

    suspend fun delete(id: String): Either<Failure, None> {
        return handleRequest {
            val query = DeleteQuery(AnswerConstants.TABLE, AnswerConstants.ID).getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            statement.executeUpdate()
            None
        }

    }

    private fun getAnswer(resultSet: ResultSet): Answer {
        val answerId = resultSet.getString(AnswerConstants.ID)
        val questionId = resultSet.getString(AnswerConstants.QUESTION_ID)
        val answer = resultSet.getString(AnswerConstants.ANSWER)
        val isCorrect = resultSet.getBoolean(AnswerConstants.IS_CORRECT)
        return Answer(id = answerId, questionId = questionId, answer = answer, isCorrect = isCorrect)
    }
}