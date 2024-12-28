package com.example.jetpackcompose.data.Api

import com.example.jetpackcompose.data.dataModel.LoginRequest
import com.example.jetpackcompose.data.dataModel.PasswordResetRequest
import com.example.jetpackcompose.data.dataModel.RegistrationRequest
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://easyfit-c2e7.onrender.com/") // Base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

fun loginUser(email: String, password: String, onTokenReceived: (String) -> Unit) {
    val loginRequest = LoginRequest(email, password)

    apiService.login(loginRequest).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    try {
                        val json = JSONObject(responseBody)
                        val token = json.getString("token") // Extract the token
                        if (token.isNotEmpty()) {
                            onTokenReceived(token)
                        } else {
                            println("Error: Token is empty")
                        }
                    } catch (e: Exception) {
                        println("Error parsing token: ${e.message}")
                    }
                } else {
                    println("Error: Empty response body")
                }
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })
}

fun registerUser(email: String, password: String,userName:String, onResponseReceived: (String) -> Unit) {
    val registerRequest = RegistrationRequest(email,userName, password)

    apiService.register(registerRequest).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    try {
                        val json = JSONObject(responseBody)
                        val message = json.getString("message") // Extract the message
                        onResponseReceived(message)
                    } catch (e: Exception) {
                        println("Error parsing message: ${e.message}")
                    }
                } else {
                    println("Error: Empty response body")
                }
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })
}

fun resetPassword(email: String, onResponseReceived: (String) -> Unit) {
    val passwordResetRequest = PasswordResetRequest(email)

    apiService.resetPassword(passwordResetRequest).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    try {
                        val json = JSONObject(responseBody)
                        val message = json.getString("message") // Extract the message
                        onResponseReceived(message)
                    } catch (e: Exception) {
                        println("Error parsing message: ${e.message}")
                    }
                } else {
                    println("Error: Empty response body")
                }
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })
}

fun confirmUser(token: String, onResponseReceived: (String) -> Unit) {
    apiService.confirmUser(token).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    onResponseReceived(responseBody)
                } else {
                    println("Error: Empty response body")
                }
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })
}




