package com.motivity.mcf_kotlin_user_management

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
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

class UserRegistrationActivityTest {

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
    fun testUserRegistrationSuccess() {
        // Enqueue a mock response for the user registration endpoint
        val responseBody = "created"
        mockWebServer.enqueue(MockResponse().setResponseCode(201).setBody("created"))

        // Perform the user registration API call
        val userRegistrationResponse = api.createUser(
            UserRegistration(
                "John",
                "Doe",
                "john.doe@example.com",
                "password",
                "1234567890",
                "+91",
                "USER"
            ),
            "application/json"
        ).execute()

        // Check if the response is successful and contains the expected message
        assert(userRegistrationResponse.isSuccessful)

        // Directly compare the response body string
       // assertEquals("created", userRegistrationResponse.body()?.string()
        assertNotNull(userRegistrationResponse.body());
    }




    @Test
    fun testUserRegistrationFailure() {
        // Enqueue a mock response for the user registration endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))

        // Perform the user registration API call
        val userRegistrationResponse = api.createUser(
            UserRegistration(
                "John",
                "Doe",
                "john.doe@example.com",
                "password",
                "1234567890",
                "+91",
                "USER"
            ),
            "application/json"
        ).execute()

        // Check if the response indicates failure
        assert(!userRegistrationResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
