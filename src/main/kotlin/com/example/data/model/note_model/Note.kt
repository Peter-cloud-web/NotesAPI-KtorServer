package com.example.data.model.note_model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id:String,
    val noteTitle:String,
    val description:String,
)
