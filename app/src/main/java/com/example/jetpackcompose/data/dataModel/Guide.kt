package com.example.jetpackcompose.data.dataModel

data class Guide(
    val id: String,
    val title: String,
    val description: String,
    // list of content including text and images
    val content: List<GuideContent>,
    val level: GuideLevel,
    val equipment: List<Equipment>,
    val bodyFocus: BodyFocus
)

data class GuideContent(
    val text: String? = null,
    val imageUrl: String? = null
)

enum class GuideLevel { BEGINNER, INTERMEDIATE, ADVANCED }
enum class Equipment { NONE, DUMBBELL, BARBELL, RESISTANCE_BAND }
enum class BodyFocus { UPPER_BODY, LOWER_BODY, FULL_BODY }