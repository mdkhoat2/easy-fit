package com.example.jetpackcompose.data.dataModel


data class ForumPost(
    val id: String,
    val user: User, // User who created the thread
    val title: String, // Title of the thread
    val content: String, // Main text of the thread
    val tags: List<String> = emptyList(), // Tags for categorization (e.g., #push_up)
    val upvote: Int = 0, // Number of upvotes
    val downvote: Int = 0, // Number of downvotes
    val comments: List<String> = emptyList(), // List of comment IDs
    val timestamp: Long // Time when the thread was created
)



data class ForumComment(
    val id: String, // Unique identifier for the comment
    val user: User, // User who created the comment
    val threadId: String, // ID of the thread the comment belongs to
    val content: String, // Text of the comment
    val upvote: Int = 0, // Number of upvotes
    val downvote: Int = 0, // Number of downvotes
    val timestamp: Long // Time when the comment was created
)
