package com.example.data.authentication_model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email:String,
    val password:String
)
