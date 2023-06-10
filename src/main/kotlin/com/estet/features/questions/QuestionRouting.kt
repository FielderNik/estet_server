package com.estet.features.questions

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureQuestion(questionService: QuestionService) {

    routing {
        post("/question") {
            val question = call.receive<QuestionRequest>()
            questionService.create(question.question, question.level, question.artType, question.score, question.description)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, "Question not created")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.Created, it)

                }
        }

        get("/question/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            questionService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read question")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/questions_all") {
            questionService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read questions")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        put("/question/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val question = call.receive<Question>()
            questionService.update(id, question)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update question")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }

        }

        delete("/question/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            questionService.delete(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't delete question")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }
        }
    }
}