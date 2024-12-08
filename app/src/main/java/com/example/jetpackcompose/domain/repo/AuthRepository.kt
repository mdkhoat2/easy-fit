package com.example.jetpackcompose.domain.repo


interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun resetPassword(email: String): Boolean
    suspend fun validateOTP(email: String, otp: String): Boolean
    suspend fun register(email: String, username: String, password: String): Boolean
    suspend fun getCurrentUserID(): String
}

