package com.motivity.mcf_kotlin_user_management

import com.motivity.mcf_kotlin_user_management.api.Api
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

class VerifyOtpTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: Api

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Test
    fun testVerifyOtpSuccess() {
        // Enqueue a mock response for the verify OTP endpoint
        val responseBody = "success"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // Perform the verify OTP API call
        val verifyOtpResponse = api.verifyOtp("123456", "+911234567890", "application/json")
            .execute()

        // Check if the response is successful and contains the expected message
        assert(verifyOtpResponse.isSuccessful)
        assertNotNull(verifyOtpResponse.body())
        assertEquals(responseBody, verifyOtpResponse.body())
    }

    @Test
    fun testVerifyOtpFailure() {
        // Enqueue a mock response for the verify OTP endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))

        // Perform the verify OTP API call
        val verifyOtpResponse = api.verifyOtp("654321", "+911234567890", "application/json")
            .execute()

        // Check if the response indicates failure
        assert(!verifyOtpResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    // Add more test cases as needed

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
