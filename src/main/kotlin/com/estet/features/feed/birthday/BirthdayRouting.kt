package com.estet.features.feed.birthday

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureBirthday(dbConnection: Connection) {
    val birthdayService = BirthdayService(dbConnection)

    routing {
        post("/birthday") {
            val birthday = call.receive<BirthdayRequest>()
            birthdayService.create(birthday.name, birthday.image)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, "Birthday not created")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.Created, it)

                }
        }

        get("/birthday/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            birthdayService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read birthday")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/birthdays_all") {
            birthdayService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read birthday")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        put("/birthday/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val birthday = call.receive<Birthday>()
            birthdayService.update(id, birthday)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update birthday")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }

        }

        delete("/birthday/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            birthdayService.delete(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update birthday")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }
        }
    }
}