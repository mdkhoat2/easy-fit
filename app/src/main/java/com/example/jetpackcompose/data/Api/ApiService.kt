package com.example.jetpackcompose.data.Api

import com.example.jetpackcompose.data.dataModel.LoginRequest
import com.example.jetpackcompose.data.dataModel.PasswordResetRequest
import com.example.jetpackcompose.data.dataModel.RegistrationRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun login(@Body request: LoginRequest): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("user/register")
    fun register(@Body request: RegistrationRequest): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/user/request-otp")
    fun resetPassword(@Body request: PasswordResetRequest): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("user/confirm/{token}")
    fun confirmUser(@Path("token") token: String): Call<ResponseBody>
}



