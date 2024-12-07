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
        Validator.validateNonEmpty(postId, "Post ID")
        return repository.getForumPost(postId)
    }
}

class createForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumPost: ForumPost): Boolean {
        Validator.validateNonEmpty(forumPost.title, "Post Title")
        return repository.createForumPost(forumPost)
    }
}

class updateForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumPost: ForumPost): Boolean {
        Validator.validateNonEmpty(forumPost.title, "Post Title")
        return repository.updateForumPost(forumPost)
    }
}

class deleteForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String): Boolean {
        Validator.validateNonEmpty(postId, "Post ID")
        return repository.deleteForumPost(postId)
    }
}



// Forum Comment Use Cases

class getForumCommentsUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String,skip:Int): List<ForumComment> {
        Validator.validateNonEmpty(postId, "Post ID")
        return repository.getForumComments(postId,skip)
    }
}

class createForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumComment: ForumComment): Boolean {
        Validator.validateNonEmpty(forumComment.content, "Comment Content")
        return repository.createForumComment(forumComment)
    }
}

class updateForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(forumComment: ForumComment): Boolean {
        Validator.validateNonEmpty(forumComment.content, "Comment Content")
        return repository.updateForumComment(forumComment)
    }
}

class deleteForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(commentId: String): Boolean {
        Validator.validateNonEmpty(commentId, "Comment ID")
        return repository.deleteForumComment(commentId)
    }
}

// Voting Use Cases

class voteForumPostUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(postId: String, isUpvote: Boolean): Boolean {
        Validator.validateNonEmpty(postId, "Post ID")
        return repository.voteForumPost(postId, isUpvote)
    }
}

class voteForumCommentUseCase(private val repository: ForumRepository) {
    suspend operator fun invoke(commentId: String, isUpvote: Boolean): Boolean {
        Validator.validateNonEmpty(commentId, "Comment ID")
        return repository.voteForumComment(commentId, isUpvote)
    }
}
