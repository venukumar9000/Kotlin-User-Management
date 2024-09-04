package com.motivity.mcf_kotlin_user_management
// ApiUnitTest.kt

import com.google.gson.GsonBuilder
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.models.UserLogin
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: Api

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(Api::class.java)
    }

    @Test
    fun testUserLoginSuccess() {
        // Enqueue a mock response for the userLogin endpoint
        val responseBody = "{\"accessToken\": \"fakeToken\", \"email\": \"test@example.com\"}"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // Perform the userLogin API call
        val userLoginResponse = api.userLogin(UserLogin("test@example.com", "password"), "application/json").execute()

        // Check if the response is successful
        assert(userLoginResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    @Test
    fun testUserLoginFailure() {
        // Enqueue a mock response for the userLogin endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(401).setBody("{\"error\": \"Unauthorized\"}"))

        // Perform the userLogin API call
        val userLoginResponse = api.userLogin(UserLogin("test@example.com", "wrongPassword"), "application/json").execute()

        // Check if the response indicates failure
        assert(!userLoginResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
