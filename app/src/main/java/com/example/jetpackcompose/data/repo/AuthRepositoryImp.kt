package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.domain.repo.AuthRepository

class AuthRepositoryImp : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Boolean = (0..1).random() == 1

    override suspend fun resetPassword(email: String): Boolean = (0..1).random() == 1

    override suspend fun validateOTP(
        email: String,
        otp: String,
    ): Boolean = (0..1).random() == 1

    override suspend fun register(
        email: String,
        username: String,
        password: String,
    ): Boolean = (0..1).random() == 1

    override suspend fun getCurrentUserID(): String = "user_${(1000..9999).random()}"
}
