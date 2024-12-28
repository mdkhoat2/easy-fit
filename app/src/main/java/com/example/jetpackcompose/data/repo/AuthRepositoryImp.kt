package com.example.jetpackcompose.data.repo

import android.content.Context
import android.util.Log
import com.example.jetpackcompose.data.Api.loginUser
import com.example.jetpackcompose.domain.repo.AuthRepository
import com.example.jetpackcompose.util.getRememberMeToken
import com.example.jetpackcompose.util.saveRememberMeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryImp (
    private val context: Context
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            saveRememberMeToken(context, "") // Clear the token
            if (getRememberMeToken(context).isNotEmpty()) {
                Log.d("AuthRepositoryImp", "Already logged in")
                return@withContext true
            }

            try {
                val token = loginUserSuspend(email, password)
                Log.d("AuthRepositoryImp", "Token: $token")
                saveRememberMeToken(context, token) // Save the token
                true
            } catch (e: Exception) {
                println("Login failed: ${e.message}")
                false
            }
        }
    }

    private suspend fun loginUserSuspend(email: String, password: String): String {
        Log.d("AuthRepositoryImp", "loginUserSuspend")
        return suspendCancellableCoroutine { continuation ->
            loginUser(email, password) { token ->
                if (token.isNotEmpty()) {
                    continuation.resume(token)
                } else {
                    continuation.resumeWithException(Exception("Token is empty"))
                }
            }
        }
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