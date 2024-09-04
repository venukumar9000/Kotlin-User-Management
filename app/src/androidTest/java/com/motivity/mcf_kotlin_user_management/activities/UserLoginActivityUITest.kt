//package com.motivity.mcf_kotlin_user_management.activities
//// UserLoginActivityUITest.kt
//
//import android.content.Context
//import androidx.test.core.app.ActivityScenario
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.typeText
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.platform.app.InstrumentationRegistry
//import com.motivity.mcf_kotlin_user_management.R
//import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
//import org.junit.Before
//import org.junit.Test
//
//class UserLoginActivityUITest {
//
//    @Before
//    fun launchActivity() {
//        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
//        ActivityScenario.launch<UserLoginActivity>(UserLoginActivity::class.java)
//    }
//
//    @Test
//    fun testLoginWithValidCredentials() {
//        // Assuming you have valid credentials for testing
//        val validEmail = "test@example.com"
//        val validPassword = "StrongP@ss1"
//
//        // Type valid email and password
//        onView(withId(R.id.username)).perform(typeText(validEmail))
//        onView(withId(R.id.password)).perform(typeText(validPassword))
//
//        // Click the login button
//        onView(withId(R.id.login)).perform(click())
//
//        // Check if the Dashboard activity is launched after successful login
//        onView(withId(R.id.dashboardLayout)).check(matches(isDisplayed()))
//    }
//
//    // Add more UI test cases as needed
//
//}
