package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.domain.repo.WorkoutRepository

class GetPatchHistoryUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(userId: String,cnt:Int=0,skip:Int=0): PatchHistory {
        Validator.validateNonEmpty(userId, "User Id")

        return repository.getPatchHistory(userId,cnt,skip)
    }
}



