package com.estet.database

import java.sql.Connection
import java.sql.DriverManager

class DatabaseFactory {

    companion object {
        fun connectToPostgres(embedded: Boolean): Connection {
            Class.forName("org.postgresql.Driver")
            if (embedded) {
                return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
            } else {
//        val url = environment.config.property("postgres.url").getString()
//        val user = environment.config.property("postgres.user").getString()
//        val password = environment.config.property("postgres.password").getString()

                val url = "jdbc:postgresql://localhost:5432/estet"

                val user = "estet"
                val password = "bgt5"

                return DriverManager.getConnection(url, user, password)
            }
        }
    }

}