//package com.motivity.mcf_kotlin_user_management.activities
//
//
//import android.util.Log
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.typeText
//import androidx.test.espresso.intent.Intents
//import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import androidx.test.platform.app.InstrumentationRegistry
//
//
//@RunWith(AndroidJUnit4::class)
//class UserLoginActivityInstrumentedTest {
//
//    @get:Rule
//    @JvmField
//    val activityRule = ActivityScenarioRule(UserLoginActivity::class.java)
//
//    private lateinit var mockWebServer: MockWebServer
//
//    @Before
//    fun setUp() {
//        try {
//            // Initialize and start the MockWebServer
//            mockWebServer = MockWebServer()
//            mockWebServer.start(8080)
//
//            // Initialize Intents for Espresso
//            Intents.init()
//        } catch (e: Exception) {
//            // Log the exception
//            e.printStackTrace()
//        }
//    }
//
//
//    @After
//    fun tearDown() {
//        // Shutdown the MockWebServer
//        mockWebServer.shutdown()
//
//        Intents.release()
//
//
//    }
//
//    @Test
//    fun loginSuccess() {
//        // Set up a mock response for a successful login
//        val mockResponse = MockResponse()
//            .setResponseCode(200)
//            .setBody("{\"accessToken\":\"dummy_token\"}")
//        mockWebServer.enqueue(mockResponse)
//
//
//
//        // Perform UI actions to trigger a login
//        onView(withId(R.id.email)).perform(typeText("Venubalijapelli9@gmail.com"))
//        onView(withId(R.id.password)).perform(typeText("Venu@12345"))
//        onView(withId(R.id.login)).perform(click())
//
//        // Check if the DashboardActivity is launched after a successful login
//        Intents.intended(hasComponent(Dashboard::class.java.name))
//    }
//}