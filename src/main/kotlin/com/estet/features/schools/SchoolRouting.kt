package com.estet.features.schools

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureSchools(dbConnection: Connection) {
    val schoolsService = SchoolService(dbConnection)

    routing {

        get("/schools/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            schoolsService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read news")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/schools_all") {
            schoolsService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read news")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }
    }
}
