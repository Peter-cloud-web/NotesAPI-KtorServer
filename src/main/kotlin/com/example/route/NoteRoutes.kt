package com.example.route

import com.example.data.authentication_model.SimpleResponse
import com.example.data.model.note_model.Note
import com.example.data.model.user_model.User
import com.example.repository.NoteRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val NOTES = "$API_VERSION/notes"
const val CREATE_NOTES = "$NOTES/create"
const val UPDATE_NOTES = "$NOTES/update"
const val DELETE_NOTES = "$NOTES/delete"
fun Route.NoteRoutes (
    db:NoteRepo,
    hashFunction:(String) -> String
){
    authenticate("jwt") {
        post(CREATE_NOTES){
            val note =  try{
                call.receive<Note>()
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Missing Fields"))
                return@post
            }
            try {
                val email = call.principal<User>()!!.email
                db.addNote(note,email)
                call.respond(HttpStatusCode.OK,SimpleResponse(true,"Note Added Successfully"))
            }catch (e:Exception){
                call.respond(HttpStatusCode.Conflict,SimpleResponse(false,e.message?:"Some problem occurred"))
            }
        }
    }

}