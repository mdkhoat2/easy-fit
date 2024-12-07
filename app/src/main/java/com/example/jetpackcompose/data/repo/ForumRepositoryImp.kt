package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.dataModel.ForumComment
import com.example.jetpackcompose.data.dataModel.ForumPost
import com.example.jetpackcompose.domain.repo.ForumRepository

class ForumRepositoryImp (): ForumRepository {
    override suspend fun getForumList(skip: Int): List<ForumPost> {
        TODO("Not yet implemented")
    }

    override suspend fun getForumPost(postId: String): ForumPost {
        TODO("Not yet implemented")
    }

    override suspend fun createForumPost(forumPost: ForumPost): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateForumPost(forumPost: ForumPost): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteForumPost(postId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getForumComments(postId: String, skip: Int): List<ForumComment> {
        TODO("Not yet implemented")
    }

    override suspend fun createForumComment(forumComment: ForumComment): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateForumComment(forumComment: ForumComment): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteForumComment(commentId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun voteForumPost(postId: String, isUpvote: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun voteForumComment(commentId: String, isUpvote: Boolean): Boolean {
        TODO("Not yet implemented")
    }
}