package com.motivity.mcf_kotlin_user_management.activities

import com.google.gson.Gson
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class UserRegistrationActivityTestapi {


    val ul = UserRegistration("venu", "john","venu@gmail.com","Venu@1234","9000909099","+91","User")
//    data class LoginResponse(val loginResponse: com.motivity.mcf_kotlin_user_management.models.LoginResponse)

    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var api: com.motivity.mcf_kotlin_user_management.api.Api

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("http://localhost:7071/api/user/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun testUserLoginApi() = runBlocking {

//        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
        val contentType = "application/json"
        // Enqueue a mock response

        val call: Call<String> = api.createUser(ul, contentType)

        try {
            val response = call.execute()

            // TODO this failure scenario remove ! in if
            if (!response.isSuccessful) {
                  response.body().toString()
                val registerStatus: Int = response.code()
                println("failure response..")
                Assert.assertEquals(registerStatus, 500);
            }

// TODO uncommit code for success response
//                else if (loginResponse != null) {
//                    val accessToken = loginResponse.accessToken
//                    val email = loginResponse.email
//                    val refreshToken = loginResponse.refreshToken
//                    val userName = loginResponse.userName
//                    Assert.assertEquals(email, mockResponse.body()?.email)
//                    Assert.assertEquals(accessToken,String.javaClass)
//                    Assert.assertEquals(refreshToken,String.javaClass)
//                    Assert.assertEquals(userName,String.javaClass)
//                } else {
//                    println("failed.....");
//                }
//                    val errorBody = response.errorBody()?.string()
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Test
    @Throws(Exception::class)
    fun testRegistration() = runBlocking {

//        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
        val contentType = "application/json"
        // Enqueue a mock response

        val call: Call<String> = api.createUser(ul, contentType)

        try {
            val response = call.execute()

            // TODO this failure scenario remove ! in if
            if (response.isSuccessful) {
                response.body().toString()
                val registerStatus: Int = response.code()
                println("failure response..")
                Assert.assertEquals(registerStatus, 200);
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

@Test
    fun testRegistration1 () {
        val ss = listOf(UserRegistration("firstName", "lastName", "email", "password",
            "contactNumber"," countryCode","admin"));
       val x = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(Gson().toJson(ss))

        mockWebServer.enqueue(x);
        assertNotNull(x.getBody());


//        {
//            "firstName": "string",
//            "lastName": "string",
//            "emailAddress": "string",
//            "password": "string",
//            "contactNumber": 0,
//            "countryCode": "string",
//            "role": "string"
//        }
    }

}