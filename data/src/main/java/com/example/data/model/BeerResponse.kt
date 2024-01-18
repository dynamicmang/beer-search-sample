package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerResponse(
    val id: Int = 0,
    val name: String = "",
    val tagline: String = "",
    val description: String = "",
    @SerialName("image_url") val imageUrl: String? = null,
)