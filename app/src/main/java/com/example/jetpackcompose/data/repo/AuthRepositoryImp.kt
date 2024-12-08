package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.domain.repo.AuthRepository

class AuthRepositoryImp () : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun validateOTP(email: String, otp: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun register(email: String, username: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserID(): String {
        TODO("Not yet implemented")
    }
}