package com.estet.features.feed.news

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureNews(dbConnection: Connection) {
    val newsService = NewsService(dbConnection)

    routing {

        get("/news/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            newsService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read news")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/news_all") {
            newsService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read news")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }
    }
}
