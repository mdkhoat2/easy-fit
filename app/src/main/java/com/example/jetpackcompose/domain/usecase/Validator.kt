package com.example.jetpackcompose.domain.usecase

object Validator {
    fun validateNonEmpty(field: String, fieldName: String) {
        if (field.isEmpty()) {
            throw ValidationException("$fieldName must not be empty")
        }
    }

    //check if an list is empty
    fun validateNonEmptyList(list: List<*>, fieldName: String) {
        if (list.isEmpty()) {
            throw ValidationException("$fieldName must not be empty")
        }
    }

    fun validateEmail(email: String) {
        validateNonEmpty(email, "Email")
        if (!ValidationUtils.isValidEmail(email)) throw ValidationException(ErrorMessages.INVALID_EMAIL)
    }

    fun validatePassword(password: String) {
        validateNonEmpty(password, "Password")
        if (password.length < 6) throw ValidationException(ErrorMessages.SHORT_PASSWORD)
    }

    fun validateUsername(username: String) {
        if (username.isEmpty() || username.length < 3) {
            throw ValidationException(ErrorMessages.SHORT_USERNAME)
        }
    }

}

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

object ErrorMessages {
    const val INVALID_EMAIL = "Email is not valid"
    const val SHORT_PASSWORD = "Password must be at least 6 characters"
    const val SHORT_USERNAME = "Username must be at least 3 characters"
}

class ValidationException(message: String) : Exception(message)