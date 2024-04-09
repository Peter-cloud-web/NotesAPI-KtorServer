package com.example.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class SimpleResponse(
    val success:Boolean,
    val message:String
)
