package com.example.domain.model

data class Beer(
    val id: String = "",
    val name: String = "",
    val tagline: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    var isBookmark: Boolean = false,
)