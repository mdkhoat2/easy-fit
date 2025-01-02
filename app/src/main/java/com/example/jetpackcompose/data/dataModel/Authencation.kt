package com.example.jetpackcompose.data.dataModel

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegistrationRequest(
    val email: String,
    val username: String,
    val password: String
)

data class PasswordResetRequest(
    val email: String
)

data class TokenResponse(
    val token: String
)

data class AuthRequest(
    val email: String,
    val password: String? = null,
    val otp: String? = null
)
