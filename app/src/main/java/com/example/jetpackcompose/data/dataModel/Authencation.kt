package com.example.jetpackcompose.data.dataModel

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegistrationRequest(
    val email: String,
    val username: String,
    val password: String,
    val confirmPassword: String
)

data class TokenResponse(
    val token: String
)

data class PasswordResetRequest(
    val email: String
)

data class OTPValidationRequest(
    val email: String,
    val otp: String
)
