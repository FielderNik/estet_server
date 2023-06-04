package com.estet.routing

import com.estet.database.DatabaseFactory
import com.estet.features.birthday.configureBirthday
import com.estet.features.questions.QuestionService
import com.estet.features.questions.configureQuestion
import com.estet.features.user.UserService
import com.estet.features.user.configureUser
import com.estet.plugins.configureCities
import com.estet.plugins.configurePageRouting
import io.ktor.server.application.*
import java.sql.Connection

fun Application.configureRouting() {

    val dbConnection: Connection = DatabaseFactory.connectToPostgres(embedded = false)
    val userService = UserService(dbConnection)
    val questionService = QuestionService(dbConnection)

    configureCities(dbConnection)
    configureBirthday(dbConnection)
    configureUser(userService)
    configureQuestion(questionService)
    configurePageRouting()
}