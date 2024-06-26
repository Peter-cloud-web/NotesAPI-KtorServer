package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.repository.NoteRepo
import com.example.route.UserRoutes
import com.example.repository.UserRepo
import com.example.route.NoteRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    val db = UserRepo()
    val noteDb = NoteRepo()
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
        NoteRoutes(noteDb,hashFunction)
    }
}
