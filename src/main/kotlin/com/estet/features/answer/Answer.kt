package com.estet.features.answer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: String,
    @SerialName("question_id")
    val questionId: String,
    val answer: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
)

@Serializable
data class AnswerRequest(
    @SerialName("question_id")
    val questionId: String,
    val answer: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
)
