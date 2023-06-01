package com.estet

import com.estet.database.DatabaseFactory
import com.estet.features.birthday.configureBirthday
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.estet.plugins.*
import java.sql.Connection

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.0.107", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val dbConnection: Connection = DatabaseFactory.connectToPostgres(embedded = false)


    configureSerialization()
    configureCities(dbConnection)
    configureBirthday(dbConnection)
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
