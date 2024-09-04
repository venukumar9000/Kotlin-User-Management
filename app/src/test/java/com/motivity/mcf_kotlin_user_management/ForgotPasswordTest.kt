//package com.motivity.mcf_kotlin_user_management
//import com.motivity.mcf_kotlin_user_management.activities.ForgotPassword
//import com.motivity.mcf_kotlin_user_management.api.Api
//import com.motivity.mcf_kotlin_user_management.models.UserRegistration
//import kotlinx.coroutines.runBlocking
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.MockitoAnnotations
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class ForgotPasswordTest {
////
////    @Test
////    fun testIsValidEmail() {
////        val forgotPassword = ForgotPassword()
////        assert(forgotPassword.isValidEmail("test@example.com"))
////        assert(!forgotPassword.isValidEmail("invalid-email"))
////    }
////
////    @Test
////    fun testIsValidPhone() {
////        val forgotPassword = ForgotPassword()
////        assert(forgotPassword.isValidPhone("+911234567890"))
////        assert(!forgotPassword.isValidPhone("invalid-phone"))
////    }
////
////    // Add more unit test cases as needed
//
////    val ul = loginId("venu", "john","venu@gmail.com","Venu@1234","9000909099","+91","User")
//////    data class LoginResponse(val loginResponse: com.motivity.mcf_kotlin_user_management.models.LoginResponse)
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
//
//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testForgorPassowrd() = runBlocking {
//
////        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
//        val contentType = "application/json"
//        // Enqueue a mock response
//
//        val call: Call<String> = api.forgotPassword("loginId", contentType)
//
//        try {
//            val response = call.execute()
//
//            // TODO this failure scenario remove ! in if
//            if (!response.isSuccessful) {
//                response.body().toString()
//                val registerStatus: Int = response.code()
//                println("failure response..")
//                Assert.assertEquals(registerStatus, 500);
//            }
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//    @Test
//    @Throws(Exception::class)
//    fun testForgorPassoword() = runBlocking {
//
////        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
//        val contentType = "application/json"
//        // Enqueue a mock response
//
//        val call: Call<String> = api.forgotPassword("loginId", contentType)
//
//        try {
//            val response = call.execute()
//
//            // TODO this failure scenario remove ! in if
//            if (response.isSuccessful) {
//                response.body().toString()
//                val registerStatus: Int = response.code()
//                println("failure response..")
//                Assert.assertEquals(registerStatus, 200);
//            }
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//}