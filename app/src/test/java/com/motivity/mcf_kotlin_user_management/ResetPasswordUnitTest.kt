package com.motivity.mcf_kotlin_user_management//package com.motivity.mcf_kotlin_user_management
//
//import com.motivity.mcf_kotlin_user_management.activities.ResetPassword
//import com.motivity.mcf_kotlin_user_management.api.Api
//import com.motivity.mcf_kotlin_user_management.models.LoginResponse
//import com.motivity.mcf_kotlin_user_management.models.UserForgot
//import com.motivity.mcf_kotlin_user_management.models.UserLogin
//import kotlinx.coroutines.runBlocking
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Assert
//import org.junit.Assert.assertTrue
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.MockitoAnnotations
//import retrofit2.Call
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class com.motivity.mcf_kotlin_user_management.ResetPasswordUnitTest {
//    val ul = UserForgot("test@example.com", "Sajdg@123","Vssaf@12344")
//
//    private lateinit var mockWebServer: MockWebServer
//
//    @Mock
//    private lateinit var api: com.motivity.mcf_kotlin_user_management.api.Api
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        mockWebServer = MockWebServer()
//        api = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("http://localhost:7071/api/user/"))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(Api::class.java)
//    }
//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testRestPassword() = runBlocking {
//        val contentType = "application/json"
//        val call: Call<String> = api.resetPassword(ul, contentType)
//
//        try {
//            val response = call.execute()
//
//            // TODO this failure scenario remove ! in if
//            if (!response.isSuccessful) {
//                 response.body().toString()
//                val resetStatus: Int = response.code()
//                println("failure response..")
//                Assert.assertEquals(resetStatus, 500);
//            }
//
//        }catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//    }
//    @Test
//    @Throws(Exception::class)
//    fun testResetPassword() = runBlocking {
//
//        val contentType = "application/json"
//        val call: Call<String> = api.resetPassword(ul, contentType)
//
//        try {
//            val response = call.execute()
//
//            // TODO this failure scenario remove ! in if
//            if (response.isSuccessful) {
//                response.body().toString()
//                val resetStatus: Int = response.code()
//                println("success response..")
//                Assert.assertEquals(resetStatus, 200);
//            }
//
//        }catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//    }
//}
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.models.UserForgot
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

class ResetPasswordUnitTest {

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
    fun testResetPasswordSuccess() {
        // Enqueue a mock response for the reset password endpoint
        val responseBody = "success"
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // Perform the reset password API call
        val resetPasswordResponse = api.resetPassword(UserForgot("john.doe@example.com", "newPassword", "newPassword"), "application/json")
            .execute()

        // Check if the response is successful and contains the expected message
        assert(resetPasswordResponse.isSuccessful)
        assertNotNull(resetPasswordResponse.body())
        assertEquals(responseBody, resetPasswordResponse.body())
    }

    @Test
    fun testResetPasswordFailure() {
        // Enqueue a mock response for the reset password endpoint with an error
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))

        // Perform the reset password API call
        val resetPasswordResponse = api.resetPassword(UserForgot("john.doe@example.com", "newPassword", "newPassword"), "application/json")
            .execute()

        // Check if the response indicates failure
        assert(!resetPasswordResponse.isSuccessful)

        // Add more assertions based on your API response and logic
    }

    // Add more test cases as needed

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
