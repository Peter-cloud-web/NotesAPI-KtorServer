package com.example.data.authentication_model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success:Boolean,
    val message:String
)
