package com.estet.features.birthday

import kotlinx.serialization.Serializable

@Serializable
data class Birthday(val id: String, val name: String, val image: String)

@Serializable
data class BirthdayRequest(val name: String, val image: String)