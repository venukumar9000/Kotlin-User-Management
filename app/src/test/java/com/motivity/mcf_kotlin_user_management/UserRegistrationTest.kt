//package com.motivity.mcf_kotlin_user_management
//import android.content.Intent
//import androidx.core.content.ContextCompat.startActivity
//import androidx.test.core.app.launchActivity
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.motivity.mcf_kotlin_user_management.activities.UserRegistrationActivity
//import com.motivity.mcf_kotlin_user_management.api.Api
//import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
//import com.motivity.mcf_kotlin_user_management.models.UserRegistration
//import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
////import androidx.test.rule.ActivityTestRule
//import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
//import com.nhaarman.mockitokotlin2.*
//import okhttp3.ResponseBody
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mockito.mockStatic
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//@RunWith(AndroidJUnit4::class)
//class UserRegistrationTest {
//
//    private lateinit var userRegistrationActivity: UserRegistrationActivity
//    private lateinit var retrofitClient: RetrofitClient
//    private lateinit var api:Api
//
//
//    @Before
//    fun setup() {
//
//
//
//
//            userRegistrationActivity = spy(UserRegistrationActivity())
//        retrofitClient = mock()
//        mockStatic(RetrofitClient::class.java)
//        whenever(RetrofitClient.instance).thenReturn(api)
//    }
//
//    @Test
//    fun testRegistrationSuccess() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration()
//        val call: Call<String> = mock()
//        whenever(api.createUser(userRegistration, "application/json")).thenReturn(call)
//        whenever(call.enqueue(any())).doAnswer {
//            val callback = it.arguments[0] as Callback<String>
//            val successResponse = Response.success("Registration successful.")
//            callback.onResponse(call, successResponse)
//        }
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Validate the expected behavior
//        verify(userRegistrationActivity).startActivity(any())
//    }
//
//    @Test
//    fun testRegistrationFailure() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration()
//        val call: Call<String> = mock()
//        whenever(api.createUser(userRegistration, "application/json")).thenReturn(call)
//        whenever(call.enqueue(any())).doAnswer {
//            val callback = it.arguments[0] as Callback<String>
//            val errorResponse = Response.error<String>(400, ResponseBody.create(null, "Registration failed."))
//            callback.onResponse(call, errorResponse)
//        }
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Validate the expected behavior
//       // verify(userRegistrationActivity).showToast("Registration failed.")
//    }
//
//    // Inside your UserRegistrationActivityTest class
//
//    @Test
//    fun testValidationEmptyFields() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration(firstName = "", lastName = "", email = "", password = "", contactNumber = "")
//
//        // Mock or stub the showError method
//        doNothing().whenever(userRegistrationActivity).showError(any())
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Verify that showError was called for each field
//        verify(userRegistrationActivity).showError("First name is required")
//        verify(userRegistrationActivity).showError("Last name is required")
//        verify(userRegistrationActivity).showError("Email is required")
//        verify(userRegistrationActivity).showError("Password is required")
//        verify(userRegistrationActivity).showError("Phone number is required")
//
//        // Verify that showToast was not called
//        verify(userRegistrationActivity, never()).showToast(any())
//    }
//
//
//    @Test
//    fun testValidationInvalidEmail() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration(email = "invalid_email")
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Validate the expected behavior
//        verify(userRegistrationActivity, never()).startActivity(any())
//        verify(userRegistrationActivity).showError("Invalid email address")
//    }
//
//    @Test
//    fun testValidationInvalidPassword() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration(password = "weak_password")
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Validate the expected behavior
//        verify(userRegistrationActivity, never()).startActivity(any())
//        verify(userRegistrationActivity).showError("Password must be at least 8 characters, including one symbol, one uppercase letter, and one digit.")
//    }
//
//    @Test
//    fun testValidationInvalidPhoneNumber() {
//        // Mocking dependencies
//        val userRegistration = mockUserRegistration(contactNumber = "123")
//
//        // Execute the method under test
//        userRegistrationActivity.onCreate(null)
//
//        // Validate the expected behavior
//        verify(userRegistrationActivity, never()).startActivity(any())
//        verify(userRegistrationActivity).showError("Invalid phone number. It should have 10 digits.")
//    }
//
//    // Add more test cases to cover other scenarios, such as successful registration, validation checks, and error handling.
//
//    private fun mockUserRegistration(
//        firstName: String = "John",
//        lastName: String = "Doe",
//        email: String = "john.doe@example.com",
//        password: String = "Password123",
//        contactNumber: String = "1234567890"
//    ): UserRegistration {
//        return UserRegistration(firstName, lastName, email, password, contactNumber, "+91", "USER")
//    }
//}
//
//@Before
//    private fun startActivity(intent: Intent) {
//        launchActivity<UserRegistrationActivity>().use { scenario ->
//            scenario.onActivity { activity ->
//                startActivity(Intent(activity, UserLoginActivity::class.java))
//                val originalActivityState = scenario.state
//            }
//        }
//    }
//
