package com.motivity.mcf_kotlin_user_management
import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.motivity.mcf_kotlin_user_management.activities.ResetPassword
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.models.UserForgot
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@RunWith(AndroidJUnit4::class)
class ResetPasswordInstrumentationTest {

    @get:Rule
    val activityRule = ActivityTestRule(ResetPassword::class.java)

    @Test
    fun testBackButtonNavigation() {
        onView(withId(R.id.backButton)).perform(click())

        // Add assertions for the expected behavior after clicking the back button
        onView(withId(R.id.forgotEmail)).check(matches(isDisplayed()))
    }

    @Test
    fun testPasswordResetSuccess() {
        // Perform actions to enter valid data and click the submit button
        onView(withId(R.id.changePasswordId)).perform(typeText("exampleId"), closeSoftKeyboard())
        onView(withId(R.id.resetForgotPassword)).perform(typeText("ValidPassword123@"), closeSoftKeyboard())
        onView(withId(R.id.conformForgotPasssword)).perform(typeText("ValidPassword123@"), closeSoftKeyboard())
        onView(withId(R.id.resetButton)).perform(click())

        // Add assertions for the expected behavior after a successful password reset
        onView(withId(R.id.resetForgotPassword)).check(matches(isDisplayed()))
    }
    // Create a custom IdlingResource for the toast
   // val toastIdlingResource = ToastIdlingResource()
    @Test
    fun testPasswordResetFailure() {
        onView(withId(R.id.changePasswordId)).perform(typeText("exampleId"), closeSoftKeyboard())
        onView(withId(R.id.resetForgotPassword)).perform(typeText("Venkk@12345"), closeSoftKeyboard())
        onView(withId(R.id.conformForgotPasssword)).perform(typeText("Venu@12345"), closeSoftKeyboard())
        onView(withId(R.id.resetButton)).perform(click())

        // Wait for the toast to appear (adjust the duration based on your app's Toast duration)
        onView(withText("Passwords do not match")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        onView(withId(R.id.conformForgotPasssword)).check(matches(isDisplayed()))
    }


//
//    private lateinit var mockWebServer: MockWebServer
//    private lateinit var api: Api
//
//    @Before
//    fun setup() {
//        mockWebServer = MockWebServer()
//
//        api = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/"))
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
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
//    fun testResetPasswordSuccess() {
//        // Enqueue a mock response for the reset password endpoint
//        val responseBody = "success"
//        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))
//
//        // Start the activity with the necessary intent data
//        val intent = Intent().apply {
//            putExtra("loginId", "john.doe@example.com")
//        }
//        activityRule.launchActivity(intent)
//
//        // Perform UI interactions using Espresso
//        onView(withId(R.id.changePasswordId)).perform(typeText("newPassword"), closeSoftKeyboard())
//        onView(withId(R.id.resetForgotPassword)).perform(typeText("VenuK@1234"),
//            closeSoftKeyboard()
//        )
//        onView(withId(R.id.conformForgotPasssword)).perform(typeText("VenuK@1234"),
//            closeSoftKeyboard()
//        )
//        Thread.sleep(100)
//        onView(withId(R.id.resetButton)).perform(click())
//
//        // Check if the expected success message is displayed
//        onView(withText("success")).check(matches(isDisplayed()))
//
//        // Check if the API call was made and the expected data is sent
//        val request = mockWebServer.takeRequest()
//        assertEquals("/reset-password", request.path)
//        assertEquals("POST", request.method)
//        assertEquals("application/json", request.getHeader("Content-Type"))
//
//        // Deserialize the JSON string using Gson
//        val gson = Gson()
//        val userForgot = gson.fromJson(request.body.readUtf8(), UserForgot::class.java)
//
//        // Check the deserialized object properties
//        assertEquals("john.doe@example.com", userForgot.email)
//       // assertEquals("newPassword", userForgot.newPassword)
//    }
//
//    @Test
//    fun testResetPasswordFailure() {
//        // Enqueue a mock response for the reset password endpoint with an error
//        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\": \"Bad Request\"}"))
//
//        // Start the activity with the necessary intent data
//        val intent = Intent().apply {
//            putExtra("loginId", "john.doe@example.com")
//        }
//        activityRule.launchActivity(intent)
//
//        // Perform UI interactions using Espresso
//        onView(withId(R.id.changePasswordId)).perform(typeText("newPassword"))
//        onView(withId(R.id.resetForgotPassword)).perform(typeText("newPassword"))
//        onView(withId(R.id.conformForgotPasssword)).perform(typeText("newPassword"))
//        onView(withId(R.id.resetButton)).perform(click())
//
//        // Check if the expected error message is displayed
//        onView(withText("Failed to process request.Invalid code")).check(matches(isDisplayed()))
//
//        // Check if the API call was made and the expected data is sent
//        val request = mockWebServer.takeRequest()
//        assertEquals("/reset-password", request.path)
//        assertEquals("POST", request.method)
//        assertEquals("application/json", request.getHeader("Content-Type"))
//
//        // Deserialize the JSON string using Gson
//        val gson = Gson()
//        val userForgot = gson.fromJson(request.body.readUtf8(), UserForgot::class.java)
//
//        // Check the deserialized object properties
//        assertEquals("john.doe@example.com", userForgot.email)
//        assertEquals("newPassword", userForgot.newPassword)
//    }
//
//

}




