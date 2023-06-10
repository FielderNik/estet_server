package com.estet.features.statistics

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureStatistics(statisticsService: StatisticsService) {

    routing {
        post("/statistics") {
            val statistics = call.receive<StatisticsRequest>()
            statisticsService.create(statistics.userId, statistics.questionId, statistics.selectedAnswerId)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, "Statistics not created")
                }
                .onSuccess {
                    val response = StatisticsResponse(it)
                    call.respond(HttpStatusCode.Created, response)

                }
        }

        get("/statistics/{user_id}") {
            val userId = call.parameters["user_id"] ?: throw IllegalArgumentException("Invalid ID")
            statisticsService.getByUserId(userId)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read statistics")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/statistics_all") {
            statisticsService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read statistics")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        put("/statistics/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val statistics = call.receive<Statistics>()
            statisticsService.update(id, statistics)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update statistics")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }

        }

        delete("/statistics/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            statisticsService.delete(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't delete statistics")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }
        }
    }
}