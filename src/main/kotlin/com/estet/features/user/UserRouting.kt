package com.estet.features.user

import com.estet.utils.funcional.onFailure
import com.estet.utils.funcional.onSuccess
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUser(userService: UserService) {

    routing {
        post("/user") {
            val user = call.receive<UserRequest>()
            userService.create(user.name, user.email, user.avatarUrl)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, "User not created")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.Created, it)

                }
        }

        get("/create_empty_user") {
            userService.create("", null, null)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read user")
                }
                .onSuccess {
                    val response = UserResponse(it)
                    call.respond(HttpStatusCode.Created, response)
                }
        }

        get("/user/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            userService.getById(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read user")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        get("/users_all") {
            userService.getAll()
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't read user")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK, it)
                }
        }

        put("/user/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<User>()
            userService.update(id, user)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update user")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }

        }

        delete("/user/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            userService.delete(id)
                .onFailure {
                    call.respond(HttpStatusCode.BadRequest, it.exception?.message ?: "Error: can't update user")
                }
                .onSuccess {
                    call.respond(HttpStatusCode.OK)
                }
        }
    }
}