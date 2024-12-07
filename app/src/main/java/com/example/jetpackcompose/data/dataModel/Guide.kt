package com.example.jetpackcompose.data.dataModel

data class Guide(
    val id: String,
    val title: String,
    val description: String,
    // list of content including text and images
    val content: List<GuideContent>,
    val level: String,
    val equipment: String,
    val bodyFocus: String,
)

data class GuideContent(
    val text: String? = null,
    val imageUrl: String? = null
)