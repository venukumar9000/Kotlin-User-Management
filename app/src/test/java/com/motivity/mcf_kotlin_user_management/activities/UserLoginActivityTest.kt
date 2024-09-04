package com.motivity.mcf_kotlin_user_management.activities

//import com.motivity.mcf_kotlin_user_management.api.Api
//import com.motivity.mcf_kotlin_user_management.models.LoginResponse
//import com.motivity.mcf_kotlin_user_management.models.UserLogin
//import com.nhaarman.mockitokotlin2.whenever
//import junit.framework.Assert.assertEquals
//import junit.framework.Assert.assertFalse
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.ResponseBody.Companion.toResponseBody
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertTrue
//import org.junit.Test
//import org.mockito.Mockito.*
//import retrofit2.Call
//import retrofit2.Response
//
//class UserLoginActivityTest {
//
//    // Define the Retrofit interface as a mock
//    private val api = mock<Api>()
//    @Test
//    fun `userLogin should return success response`() {
//        // Mock a successful response with HTTP status code 200
//        val successResponse = Response.success(LoginResponse(/* your response data here */))
//
//        // Mock the Call object returned by Retrofit to return the success response
//        val mockUserLoginCall = mock<Call<LoginResponse>>()
//        whenever(mockUserLoginCall.execute()).thenReturn(successResponse)
//
//        // Mock the Retrofit interface's method to return the mock UserLoginCall object
//        whenever(api.userLogin(any(), anyString())).thenReturn(mockUserLoginCall)
//
//        // Create the userLogin request
//        val userLoginRequest = UserLogin("johndoe@gmail.com", "Password@12")
//        val contentType = "Application/json" // Modify this based on your actual Content-Type
//
//        // Execute the userLogin method
//        val actualResponse = api.userLogin(userLoginRequest, contentType).execute()
//
//        // Verify that the mockRetrofitClient was called with the correct arguments
//        verify(api, times(1)).userLogin(userLoginRequest, contentType)
//
//        // Verify that the mockUserLoginCall was executed
//        verify(mockUserLoginCall, times(1)).execute()
//
//        // Assert that the actualResponse is not null
//        assertNotNull(actualResponse)
//
//        // Assert that the actualResponse is a success response
//        assertTrue(actualResponse.isSuccessful())
//
//        // If you want to check the response body, you can access it using actualResponse.body()
//        assertNotNull(actualResponse.body())
//        // Add more assertions based on the expected response data
//    }
//
//
//    @Test
//    fun `userLogin should handle error response`() {
//        // Mock an error response with HTTP status code 401
//        val errorResponse = Response.error<LoginResponse>(401, "Unauthorized".toResponseBody("text/plain".toMediaTypeOrNull()))
//
//        // Mock the Call object returned by Retrofit to return the error response
//        val mockUserLoginCall = mock<Call<LoginResponse>>()
//        whenever(mockUserLoginCall.execute()).thenReturn(errorResponse)
//
//        // Mock the Retrofit interface's method to return the mock UserLoginCall object
//        whenever(api.userLogin(any(), any())).thenReturn(mockUserLoginCall)
//
//        // Create the userLogin request with invalid credentials
//        val userLoginRequest = UserLogin("invaliduser", "invalidpassword")
//
//        // Execute the userLogin method
//        val actualResponse = api.userLogin(userLoginRequest, "Application/json").execute()
//
//        // Verify that the mockRetrofitClient was called with the correct arguments
//        verify(api, times(1)).userLogin(userLoginRequest, "Application/json")
//
//        // Verify that the mockUserLoginCall was executed
//        verify(mockUserLoginCall, times(1)).execute()
//
//        // Assert that the actualResponse is not null
//        assertNotNull(actualResponse)
//
//        // Assert that the actualResponse is an error response
//        assertFalse(actualResponse.isSuccessful)
//
//        // Assert that the error response code is 401
//        assertEquals(401, actualResponse.code())
//
//        // Assert that the error response message is "Unauthorized"
//        assertEquals("Unauthorized", actualResponse.message())
//    }
//
//}






import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.LoginResponse
import com.motivity.mcf_kotlin_user_management.models.UserLogin
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserLoginActivityTest {

    /*private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var api: Api


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        // Initialize MockWebServer
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)


    }

    @Test
    fun testLoginUser() {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = api.userLogin(UserLogin("venu@gmail.com", "2242435@Ve"), "application/json")
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.isExecuted)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }*/

//
//    interface Api : com.motivity.mcf_kotlin_user_management.api.Api {
//        @POST("login")
//        suspend fun userLogin(@Body userLogin: UserLogin): Response<LoginResponse>
//    }

//    data class UserLogin(val email: String, val password: String)

    val ul = UserLogin("venubalijapelli9@gmail.com", "Venu@12345")
    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var api: Api
    @Mock
    private lateinit var ret : RetrofitClient;

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("http://172.20.100.13:7071/api/user/"))
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
        val mockResponse = Response.success(
            LoginResponse(
                "accessToken",
                "venubalijapelli9@gmail.com",
                "refreshToken",
                "pavan lucky"
            )
        )
//        MockBackendAPI.start();
//        MockBackendAPI.mockLoginSuccessWithSuccess();
        val call: Call<LoginResponse> = api.userLogin(ul, contentType)

        try {
            val response = call.execute()

            // TODO this failure scenario remove ! in if
            if (response.isSuccessful) {
                val loginResponse: LoginResponse? = response.body()
                val loginstatus: Int = response.code()
                println("failure response..")
                Assert.assertEquals(loginstatus, 200);


// TODO uncommit code for success response
                if (loginResponse != null) {
                    val accessToken = loginResponse.accessToken
                    val email = loginResponse.email
                    val refreshToken = loginResponse.refreshToken
                    val userName = loginResponse.userName
                    Assert.assertEquals(email, mockResponse.body()?.email)
//                    Assert.assertEquals(accessToken, String.javaClass)
//                    Assert.assertEquals(refreshToken, String.javaClass)
                    Assert.assertEquals(userName, mockResponse.body()?.userName)
                }
            } else {
                println("failed.....");
            }
            val errorBody = response.errorBody()?.string()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Test
    @Throws(Exception::class)
    fun tesrr() = runBlocking {

//        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
        val contentType = "application/json"
        // Enqueue a mock response
        val mockResponse = Response.success(
            LoginResponse(
                "accessToken",
                "venubalijapelli9@gmail.com",
                "refreshsToken",
                "pavanlucky"
            )
        )
//        MockBackendAPI.start();
//        MockBackendAPI.mockLoginSuccessWithSuccess();
        val call: Call<LoginResponse> = api.userLogin(ul, contentType)

        try {
            val response = call.execute()

            // TODO this failure scenario remove ! in if
            if (!response.isSuccessful) {
                val loginResponse: LoginResponse? = response.body()
                val loginstatus: Int = response.code()
                println("failure response..")
                Assert.assertEquals(loginstatus, 500);


// TODO uncommit code for success response
                if (loginResponse != null) {
                    val accessToken = loginResponse.accessToken
                    val email = loginResponse.email
                    val refreshToken = loginResponse.refreshToken
                    val userName = loginResponse.userName
                    Assert.assertEquals(email, mockResponse.body()?.email)
                  Assert.assertEquals(accessToken, String.javaClass)
//                    Assert.assertEquals(refreshToken, String.javaClass)
                    Assert.assertEquals(userName, mockResponse.body()?.userName)
                }
            } else {
                println("failed.....");
            }
            val errorBody = response.errorBody()?.string()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Test
    fun testUserLoginSuccess() {
//        val ull = UserLogin("venubalijapelli9@gmail.com", "Venu@12345")
//        val contentType = "application/json"
//        val call: Call<LoginResponse> = api.userLogin(ull, contentType)
//        val loginResponse = LoginResponse("your_access_token")
//       val res : Response<LoginResponse> = call.execute()
//        val calll = mock(Call::class.java) as Call<LoginResponse>
//        println(api.userLogin(ull,contentType).execute().isSuccessful)
//       //val calll = Mockito.mock(Call::class.java) as Call<LoginResponse>
//        Mockito.`when`(api.userLogin(ull, contentType)).thenReturn(calll)
//        Mockito.`when`(call.execute()).thenReturn(Response.success(loginResponse))
//        // Your actual test logic goes here
//        // Call the method that triggers the login and assert the expected behavior
//
//        // Example:
//         val result = api.userLogin(ull, contentType);
//         assertEquals(res, result)

            val ull = UserLogin("venubalijapelli9@gmail.com", "Venu@12345")
            val contentType = "application/json"

            // Declare mock objects
            val apiMock: Api = mock(Api::class.java)
            val callMock: Call<LoginResponse> = mock(Call::class.java) as Call<LoginResponse>

            // Stub api.userLogin method with the mock object
            Mockito.`when`(apiMock.userLogin(ull,contentType)).thenReturn(callMock)

            // Stub call.execute method with expected response
            val loginResponse = LoginResponse("your_access_token")
            Mockito.`when`(callMock.execute()).thenReturn(Response.success(loginResponse))

            // Trigger the user login call with the mock object
            val result = apiMock.userLogin(ull, contentType)

            // Test successful execution
            assertNotNull(result);

//            // Test response body
//            val responseBody = result.body()
//            assertNotNull(responseBody)
//            assertEquals(responseBody?.token, loginResponse.token)

            // Verify call interactions
//            Mockito.verify(apiMock).userLogin(ull, contentType)
//            Mockito.verify(callMock).execute()

      //  whenever(mock(RetrofitClient.instance.userLogin(ul,contentType).execute().isSuccessful)).thenReturn(true)
    }
    @Test
    fun testApicall() {
        val mockresponse = MockResponse()
       // mockresponse.setBody("[]")
        mockWebServer.enqueue(mockresponse)

        val response = api.userLogin(ul, "application/json").execute().body()
       // mockWebServer.takeRequest()
        Assert.assertNotNull(response);
      //  Assert.assertEquals(true, response)

    }
}


//    @Test
//    @Throws(Exception::class)
//    fun testUserLogin() = runBlocking {
//
////        val userLogin = UserLogin("test@exmple.com", "2242435@Ve")
//        val contentType = "application/json"
//        // Enqueue a mock response
//        val mockResponse = Response.success(
//            LoginResponse(
//                "accessToken",
//                "venubalijapelli9@gmail.com",
//                "refreshToken",
//                "Test User"
//            )
//        )
//        val call: Call<LoginResponse> = api.userLogin(ul, contentType)
//
//        try {
//            val response = call.execute()
//
//            // TODO this failure scenario remove ! in if
//            if (response.isSuccessful) {
//                val loginResponse: LoginResponse? = response.body()
//                val loginstatus: Int = response.code()
//                println("failure response..")
//                Assert.assertEquals(loginstatus, 200);
//
//
//
//                if (loginResponse != null) {
//                    val accessToken = loginResponse.accessToken
//                    val email = loginResponse.email
//                    val refreshToken = loginResponse.refreshToken
//                    val userName = loginResponse.userName
//                    Assert.assertEquals(email, mockResponse.body()?.email)
//                    Assert.assertEquals(accessToken, String.javaClass)
//                    Assert.assertEquals(refreshToken, String.javaClass)
//                    Assert.assertEquals(userName, String.javaClass)
//                }
//            }else {
//                println("failed.....");
//            }
//            val errorBody = response.errorBody()?.string()
//
//        }catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//    }
//}

//



//    @Test
//    fun testLoginUserSuccess() {
//        // Mock a successful response from the server
//        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))
//
//        // Call the method you want to test
//        myApiClient.instance.userLogin(UserLogin("venubalijapelli9@gmail.com", "Venu@1234"), object :
//            Callback<LoginResponse> {
//            // Implement callback methods
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//
//        // Add assertions based on the expected behavior
//        // For example, verify that the correct API method is called with the expected parameters
//      // Mockito.verify(api).instance.userLogin((UserLogin("venubalijapelli9@gmail.com", "Venu@1234"))
//        Mockito.verify(api)
//    }

    // Add more test methods for different scenarios (e.g., failure cases)
//}
