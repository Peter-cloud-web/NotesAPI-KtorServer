  package com.example

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.plugins.*
import com.example.repository.DatabaseFactory
import com.example.repository.repo
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    //Create usertable in the notes_db database
    DatabaseFactory.init()


    configureSecurity()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
