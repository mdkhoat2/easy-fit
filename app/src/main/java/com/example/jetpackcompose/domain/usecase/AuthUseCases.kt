package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.domain.repo.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        Validator.validateEmail(email)
        Validator.validateNonEmpty(password, "Password")

        return repository.login(email, password)
    }
}

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String): Boolean {
        Validator.validateEmail(email)
        Validator.validateNonEmpty(password, "Password")
        Validator.validateUsername(username)

        return repository.register(email, username, password)
    }
}

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Boolean {
        Validator.validateEmail(email)

        return repository.resetPassword(email)
    }
}

class ValidateOTPUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, otp: String): Boolean {
        Validator.validateEmail(email)

        return repository.validateOTP(email, otp)
    }
}

class GetCurrentUserIDUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): String {
        return repository.getCurrentUserID()
    }
}







