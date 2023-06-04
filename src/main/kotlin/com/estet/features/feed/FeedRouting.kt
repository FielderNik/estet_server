package com.estet.features.feed

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureFeed(dbConnection: Connection) {
    val feedService = FeedService(dbConnection)

    routing {
        get("/feed") {
            feedService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read news")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }
    }
}