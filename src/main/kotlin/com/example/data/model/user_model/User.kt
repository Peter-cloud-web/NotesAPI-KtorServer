package com.example.data.model.user_model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email:String,
    val hashPassword:String,
    val userName: String
):Principal
