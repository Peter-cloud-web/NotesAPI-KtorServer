package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.data.model.User
import com.example.data.model.UserRoutes
import com.example.repository.repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    val db = repo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        UserRoutes(db,jwtService,hashFunction)
    }
}
