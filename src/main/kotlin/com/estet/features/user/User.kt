package com.estet.features.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)

@Serializable
data class UserRequest(
    val name: String,
    val email: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)
