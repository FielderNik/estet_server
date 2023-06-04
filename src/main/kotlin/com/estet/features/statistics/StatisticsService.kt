package com.estet.features.statistics

import com.estet.database.BaseService
import com.estet.database.sharedqueries.DeleteQuery
import com.estet.database.sharedqueries.GetAllQuery
import com.estet.features.statistics.queries.CreateStatisticsQuery
import com.estet.features.statistics.queries.GetByUserIdQuery
import com.estet.features.statistics.queries.UpdateStatisticsQuery
import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import com.estet.utils.funcional.None
import com.estet.utils.generateId
import java.sql.Connection
import java.sql.ResultSet

class StatisticsService(private val connection: Connection) : BaseService() {

    suspend fun create(userId: String, questionId: String, selectedAnswerId: String): Either<Failure, String> {
        return handleRequest {
            val statement = connection.prepareStatement(CreateStatisticsQuery().getQuery())
            val id = generateId()
            statement.setString(1, id)
            statement.setString(2, userId)
            statement.setString(3, questionId)
            statement.setString(4, selectedAnswerId)
            statement.executeUpdate()
            id
        }
    }

    suspend fun getByUserId(userId: String): Either<Failure, List<Statistics>> {
        return handleRequest {
            val resultList: MutableList<Statistics> = mutableListOf()

            val query = GetByUserIdQuery().getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, userId)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val statistics = getStatistics(resultSet)
                resultList.add(statistics)
            }
            resultList
        }
    }

    suspend fun getAll(): Either<Failure, List<Statistics>> {
        return handleRequest {
            val resultList: MutableList<Statistics> = mutableListOf()
            val query = GetAllQuery(StatisticsConstants.TABLE).getQuery()
            val statement = connection.prepareStatement(query)
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val statistics = getStatistics(resultSet)
                resultList.add(statistics)
            }
            resultList
        }

    }

    suspend fun update(id: String, statistics: Statistics): Either<Failure, None> {
        return handleRequest {
            val statement = connection.prepareStatement(UpdateStatisticsQuery().getQuery())
            statement.setString(1, statistics.userId)
            statement.setString(2, statistics.questionId)
            statement.setString(3, statistics.selectedAnswerId)
            statement.setString(4, id)
            statement.executeUpdate()
            None
        }
    }

    suspend fun delete(id: String): Either<Failure, None> {
        return handleRequest {
            val query = DeleteQuery(StatisticsConstants.TABLE, StatisticsConstants.ID).getQuery()
            val statement = connection.prepareStatement(query)
            statement.setString(1, id)
            statement.executeUpdate()
            None
        }

    }

    private fun getStatistics(resultSet: ResultSet): Statistics {
        val id = resultSet.getString(StatisticsConstants.ID)
        val userId = resultSet.getString(StatisticsConstants.USER_ID)
        val questionId = resultSet.getString(StatisticsConstants.QUESTION_ID)
        val selectedAnswerId = resultSet.getString(StatisticsConstants.SELECTED_ANSWER_ID)

        return Statistics(
            id = id,
            userId = userId,
            questionId = questionId,
            selectedAnswerId = selectedAnswerId
        )
    }
}