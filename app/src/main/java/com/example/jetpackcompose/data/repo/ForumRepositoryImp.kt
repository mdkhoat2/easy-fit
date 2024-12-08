package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.dataModel.ForumComment
import com.example.jetpackcompose.data.dataModel.ForumPost
import com.example.jetpackcompose.domain.repo.ForumRepository

class ForumRepositoryImp : ForumRepository {
    override suspend fun getForumList(skip: Int): List<ForumPost> =
        List(5) {
            ForumPost(
                id = "post_${(1000..9999).random()}",
                title = "Post Title ${(1..100).random()}",
                content = "Sample post content ${(1..100).random()}",
                tags = List((1..3).random()) { "tag${(1..5).random()}" },
                upvote = (0..100).random(),
                downvote = (0..50).random(),
                comments = List((0..5).random()) { "comment_${(1000..9999).random()}" },
                timestamp = System.currentTimeMillis() - (0..365 * 24 * 60 * 60 * 1000).random(),
            )
        }

    override suspend fun getForumPost(postId: String): ForumPost = getForumList(0).random()

    override suspend fun createForumPost(forumPost: ForumPost): Boolean = (0..1).random() == 1

    override suspend fun updateForumPost(forumPost: ForumPost): Boolean = (0..1).random() == 1

    override suspend fun deleteForumPost(postId: String): Boolean = (0..1).random() == 1

    override suspend fun getForumComments(
        postId: String,
        skip: Int,
    ): List<ForumComment> =
        List(5) {
            ForumComment(
                id = "comment_${(1000..9999).random()}",
                content = "This is a sample comment ${(1..100).random()}.",
                upvote = (0..100).random(),
                downvote = (0..50).random(),
                timestamp = System.currentTimeMillis() - (0..365 * 24 * 60 * 60 * 1000).random(),
                user = null,
            )
        }

    override suspend fun createForumComment(forumComment: ForumComment): Boolean = (0..1).random() == 1

    override suspend fun updateForumComment(forumComment: ForumComment): Boolean = (0..1).random() == 1

    override suspend fun deleteForumComment(commentId: String): Boolean = (0..1).random() == 1

    override suspend fun voteForumPost(
        postId: String,
        isUpvote: Boolean,
    ): Boolean = (0..1).random() == 1

    override suspend fun voteForumComment(
        commentId: String,
        isUpvote: Boolean,
    ): Boolean = (0..1).random() == 1
}
