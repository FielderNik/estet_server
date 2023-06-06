package com.estet.features.schools

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class School(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("point")
    val geoPoint: GeoPoint,
    @SerialName("opening_hours")
    val openingHours: String,
) {
    @Serializable
    data class GeoPoint(
        @SerialName("lat")
        val lat: Double,
        @SerialName("lon")
        val lon: Double
    ) {
        companion object {
            val EMPTY = GeoPoint(0.0, 0.0)
        }
    }
}