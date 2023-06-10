package com.estet.features.statistics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("question_id")
    val questionId: String,
    @SerialName("selected_answer_id")
    val selectedAnswerId: String,
)

@Serializable
data class StatisticsRequest(
    @SerialName("user_id")
    val userId: String,
    @SerialName("question_id")
    val questionId: String,
    @SerialName("selected_answer_id")
    val selectedAnswerId: String,
)