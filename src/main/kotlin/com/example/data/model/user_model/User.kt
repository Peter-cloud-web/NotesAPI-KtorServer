package com.example.data.model.user_model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email:String,
    val hashPassword:String,
    val userName: String
)