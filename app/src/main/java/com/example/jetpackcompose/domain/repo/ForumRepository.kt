package com.example.jetpackcompose.domain.repo

import com.example.jetpackcompose.data.dataModel.ForumComment
import com.example.jetpackcompose.data.dataModel.ForumPost

interface ForumRepository {
    suspend fun getForumList(skip:Int): List<ForumPost>
    suspend fun getForumPost(postId: String): ForumPost
    suspend fun createForumPost(forumPost: ForumPost): Boolean
    suspend fun updateForumPost(forumPost: ForumPost): Boolean
    suspend fun deleteForumPost(postId: String): Boolean

    suspend fun getForumComments(postId: String,skip: Int): List<ForumComment>
    suspend fun createForumComment(forumComment: ForumComment): Boolean
    suspend fun updateForumComment(forumComment: ForumComment): Boolean
    suspend fun deleteForumComment(commentId: String): Boolean // Change comment content to "Deleted"

    suspend fun voteForumPost(postId: String, isUpvote: Boolean): Boolean
    suspend fun voteForumComment(commentId: String, isUpvote: Boolean): Boolean
}