package com.estet

import com.estet.plugins.configureMonitoring
import com.estet.plugins.configureSecurity
import com.estet.plugins.configureSerialization
import com.estet.routing.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.0.107", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    configureSerialization()
    configureRouting()
    configureMonitoring()
    configureSecurity()
}
