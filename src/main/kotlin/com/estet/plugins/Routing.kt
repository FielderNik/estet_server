package com.estet.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*

fun Application.configureRouting() {
    
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }

        get("/hello") {
            call.respondHtml(HttpStatusCode.OK) {

                head {
                    title {
                        +"Hello"
                    }
                }

                body {
                    h1 {
                        +"Hello from KTOR!"
                    }

                    button {
                        title = "ONE"


                    }
                }


            }
        }
    }
}
