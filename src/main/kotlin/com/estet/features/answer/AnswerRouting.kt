package com.estet.features.answer

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAnswer(answerService: AnswerService) {

    routing {
        post("/answer") {
            val answer = call.receive<AnswerRequest>()
            answerService.create(
                questionId = answer.questionId,
                answer = answer.answer,
                isCorrect = answer.isCorrect
            )
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, "Answer not created")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.Created, it)

                }
        }

        get("/answer/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            answerService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read answer")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/answers_all") {
            answerService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read answer")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        put("/answer/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val answer = call.receive<Answer>()
            answerService.update(id, answer)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update answer")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }

        }

        delete("/answer/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            answerService.delete(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't delete answer")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }
        }
    }
}