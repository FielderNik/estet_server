package com.estet.features.questions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String,
    val question: String,
    val level: Int,
    @SerialName("art_type")
    val artType: Int,
    val score: Int,
    val description: String,
    val ordinal: Int,
)

@Serializable
data class QuestionRequest(
    val question: String,
    val level: Int,
    @SerialName("art_type")
    val artType: Int,
    val score: Int,
    val description: String,
    val ordinal: Int,
)