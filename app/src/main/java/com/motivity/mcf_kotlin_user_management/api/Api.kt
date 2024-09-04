package com.motivity.mcf_kotlin_user_management.api

import com.motivity.mcf_kotlin_user_management.models.Employee
import com.motivity.mcf_kotlin_user_management.models.LoginResponse
import com.motivity.mcf_kotlin_user_management.models.UserForgot
import com.motivity.mcf_kotlin_user_management.models.UserLogin
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("register")
    fun createUser(
        @Body userRegistration: UserRegistration,
        @Header("Content-Type") contentType : String
    ):Call<String>

    @POST("login")
    fun userLogin(
        @Body userLogin: UserLogin,
        @Header("Content-Type") contentType: String
    ):Call<LoginResponse>



    @POST("forgotpassword")
    fun forgotPassword(
        @Query("loginId") loginId: String,
        @Header("Content-Type") contentType : String
    ): Call<String>

    @POST("send-otp")
    fun sendOtp(
        @Query("toPhoneNumber") toPhoneNumber: String,
        @Header("Content-Type") contentType : String
    ): Call<String>

    @POST("verify-otp")
    fun verifyOtp(
        @Query("toPhoneNumber") toPhoneNumber: String,
        @Query("otp") otp: String,
        @Header("Content-Type") contentType : String
    ): Call<String>


    @POST("resetpassword")
    fun resetPassword(
        @Body userForgot: UserForgot,
        @Header("Content-Type") contentType : String
    ): Call<String>

    @GET("getAllEmployees")
    fun getAllEmployees(): Call<List<Employee>>
}