package com.estet.utils

import java.util.UUID

fun generateId(): String {
    return UUID.randomUUID().toString()
}