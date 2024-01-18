package com.example.domain.model

data class BeerDetail(
    val id: String = "",
    val name: String = "",
    val tagline: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    val isBookmark: Boolean = false,
)