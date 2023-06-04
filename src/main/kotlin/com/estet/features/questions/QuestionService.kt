package com.estet.features.questions

import com.estet.database.BaseService
import com.estet.database.sharedqueries.DeleteQuery
import com.estet.database.sharedqueries.GetAllQuery
import com.estet.database.sharedqueries.GetByIdQuery
import com.estet.features.questions.queries.CreateQuestionQuery
import com.estet.features.questions.queries.UpdateQuestionQuery
import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import com.estet.utils.funcional.None
import com.estet.utils.generateId
import java.sql.Connection
import java.sql.ResultSet

class QuestionService(private val connection: Connection) : BaseService() {

    suspend fun create(question: String, level: Int, artType: Int, score: Int): Either<Failure, String> {
        return handleRequest {
            val statement = connection.prepareStatement(CreateQuestionQuery().getQuery())
            val id = generateId()
            statement.setString(1, id)
            statement.setString(2, question)
            statement.setInt(3, level)
            statement.setInt(4, artType)
            statement.setInt(5, score)
            statement.executeUpdate()
            id
        }
    }

    suspend fun getById(id: String): Either<Failure, Question> {
        return handleRequest {
            val query = GetByIdQuery(QuestionConstants.TABLE, QuestionConstants.ID).getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                getQuestion(resultSet)
            } else {
                throw Exception("Record not found")
            }
        }
    }

    suspend fun getAll(): Either<Failure, List<Question>> {
        return handleRequest {
            val resultList: MutableList<Question> = mutableListOf()
            val query = GetAllQuery(QuestionConstants.TABLE).getQuery()
            val statement = connection.prepareStatement(query)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val question = getQuestion(resultSet)
                resultList.add(question)
            }
            resultList
        }

    }

    suspend fun update(id: String, question: Question): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(UpdateQuestionQuery().getQuery())
            statement.setString(1, question.question)
            statement.setInt(2, question.level)
            statement.setInt(3, question.artType)
            statement.setInt(4, question.score)
            statement.setString(5, id)
            statement.executeUpdate()
            None
        }
    }

    suspend fun delete(id: String): Either<Failure, None> {
        return handleRequest {
            val query = DeleteQuery(QuestionConstants.TABLE, QuestionConstants.ID).getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            statement.executeUpdate()
            None
        }

    }

    private fun getQuestion(resultSet: ResultSet): Question {
        val questionId = resultSet.getString(QuestionConstants.ID)
        val question = resultSet.getString(QuestionConstants.QUESTION)
        val level = resultSet.getInt(QuestionConstants.LEVEL)
        val artType = resultSet.getInt(QuestionConstants.ART_TYPE)
        val score = resultSet.getInt(QuestionConstants.SCORE)
        return Question(id = questionId, question = question, level = level, artType = artType, score = score)
    }
}