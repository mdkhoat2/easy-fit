package com.example.jetpackcompose.presentation.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.repo.AuthRepositoryImp
import com.example.jetpackcompose.domain.usecase.LoginUseCase
import com.example.jetpackcompose.presentation.di.BottomBarScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(context: android.content.Context) : ViewModel()  {
    private val loginUseCase = LoginUseCase(repository = AuthRepositoryImp(context))

    suspend fun login(email: String, password: String) : Boolean {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Login
            val isLoginSuccess = loginUseCase(email, password)
            Log.d("LoginScreen", "isLoginSuccess: $isLoginSuccess")
            if (isLoginSuccess) {
                return true
            }
        }
        else{
            Log.d("LoginScreen", "Email or password is empty")
        }
        return false
    }
}