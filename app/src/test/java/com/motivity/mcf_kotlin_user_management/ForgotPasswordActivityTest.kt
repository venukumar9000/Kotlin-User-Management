package com.motivity.mcf_kotlin_user_management

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.activities.ForgotPassword
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.io.StringReader

class ForgotPasswordActivityTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: Api

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(Api::class.java)
    }

    @Test
    fun testForgotPasswordSuccess() {
        // Enqueue a mock response for the forgot password endpoint
        val responseBody = "success"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // Perform the forgot password API call
        val forgotPasswordResponse = api.forgotPassword("john.doe@example.com", "application/json")
            .execute()

        // Check if the response is successful and contains the expected message
        assert(forgotPasswordResponse.isSuccessful)
        assertNotNull(forgotPasswordResponse.body())
        assertEquals(responseBody, forgotPasswordResponse.body())
    }

    @Test
    fun testForgotPasswordFailure() {
        // Enqueue a mock response for the forgot password endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))

        // Perform the forgot password API call
        val forgotPasswordResponse = api.forgotPassword("john.doe@example.com", "application/json")
            .execute()

        // Check if the response indicates failure
        assert(!forgotPasswordResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }
    @Test
    fun testSendOtpSuccess() {
        // Enqueue a mock response for the send OTP endpoint
        val responseBody = "success"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // Perform the send OTP API call
        val sendOtpResponse = api.sendOtp("+911234567890", "application/json").execute()

        // Check if the response is successful and contains the expected message
        assert(sendOtpResponse.isSuccessful)
        assertNotNull(sendOtpResponse.body())
        assertEquals(responseBody, sendOtpResponse.body())
    }

    @Test
    fun testSendOtpFailure() {
        // Enqueue a mock response for the send OTP endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))

        // Perform the send OTP API call
        val sendOtpResponse = api.sendOtp("+911234567890", "application/json").execute()

        // Check if the response indicates failure
        assert(!sendOtpResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
