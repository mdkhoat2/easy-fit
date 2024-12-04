package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.data.dataModel.ForumComment
import com.example.jetpackcompose.data.dataModel.ForumPost
import com.example.jetpackcompose.domain.repo.ForumRepository

class getForumPostsUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(skip:Int): List<ForumPost> {
        return repository.getForumList(skip)
    }
}

class getForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String): ForumPost {
        return repository.getForumPost(postId)
    }
}

class createForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumPost: ForumPost): Boolean {
        validateForumPost(forumPost)
        return repository.createForumPost(forumPost)
    }
}

class updateForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumPost: ForumPost): Boolean {
        validateForumPost(forumPost)
        return repository.updateForumPost(forumPost)
    }
}

class deleteForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String): Boolean {
        return repository.deleteForumPost(postId)
    }
}



// Forum Comment Use Cases

class getForumCommentsUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String,skip:Int): List<ForumComment> {
        return repository.getForumComments(postId,skip)
    }
}

class createForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumComment: ForumComment): Boolean {
        validateForumComment(forumComment)
        return repository.createForumComment(forumComment)
    }
}

class updateForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumComment: ForumComment): Boolean {
        validateForumComment(forumComment)
        return repository.updateForumComment(forumComment)
    }
}

class deleteForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(commentId: String): Boolean {
        return repository.deleteForumComment(commentId)
    }
}

fun validateForumPost(forumPost: ForumPost) {
    if (forumPost.title.isBlank()) {
        throw IllegalArgumentException("Forum post must have a title")
    }

    if (forumPost.tags.isEmpty()) {
        throw IllegalArgumentException("Forum post must have at least one tag")
    }
}

fun validateForumComment(forumComment: ForumComment) {
    if (forumComment.content.isBlank()) {
        throw IllegalArgumentException("Forum comment must have content")
    }
}


// Voting Use Cases

class voteForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String, isUpvote: Boolean): Boolean {
        return repository.voteForumPost(postId, isUpvote)
    }
}

class voteForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(commentId: String, isUpvote: Boolean): Boolean {
        return repository.voteForumComment(commentId, isUpvote)
    }
}
