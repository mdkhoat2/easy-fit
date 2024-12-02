package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.domain.repo.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            throw IllegalArgumentException("Email or password must not be empty")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("Email is not valid")
        }

        return repository.login(email, password)
    }
}

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String): Boolean {
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw IllegalArgumentException("Email, username or password must not be empty")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("Email is not valid")
        }

        if (password.length < 6) {
            throw IllegalArgumentException("Password must be at least 6 characters")
        }

        return repository.register(email, username, password)
    }
}

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Boolean {
        if (email.isEmpty()) {
            throw IllegalArgumentException("Email must not be empty")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("Email is not valid")
        }

        return repository.resetPassword(email)
    }
}

class ValidateOTPUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, otp: String): Boolean {
        return repository.validateOTP(email, otp)
    }
}


