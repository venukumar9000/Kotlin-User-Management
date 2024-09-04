package com.motivity.mcf_kotlin_user_management

import android.widget.Button
import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputLayout
import com.motivity.mcf_kotlin_user_management.activities.ForgotPassword
import com.motivity.mcf_kotlin_user_management.activities.ResetPassword
import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgotPasswordInstrumentationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForgotPassword::class.java)


    @Test
    fun testBackButtonNavigation() {
        var scenario: ActivityScenario<UserLoginActivity>? = null

        // Launch the ForgotPasswordActivity and perform the click on the back button
        activityRule.scenario.onActivity {
            val backButton = onView(withId(R.id.backButton)).perform(click())
            scenario = ActivityScenario.launch(UserLoginActivity::class.java)
        }

        // Check if the UserLoginActivity is displayed
        scenario?.onActivity {
            onView(withId(R.id.login)).check(matches(isDisplayed()))
        }
    }


    @Test
    fun testPasswordResetSuccess() {
        onView(withId(R.id.forgotEmail)).perform(typeText("test@example.com"))
        onView(withId(R.id.forgotpasswordSubmit)).perform(click())

        // Verify that the ResetPassword activity is launched
        onView(withId(R.id.resetForgotPassword)).check(matches(isDisplayed()))
    }

//    @Test
//    fun testPasswordResetFailure() {
//        onView(withId(R.id.forgotEmail)).perform(typeText("invalid-email"))
//        onView(withId(R.id.forgotpasswordSubmit)).perform(click())
//
//        // Verify that a toast message is displayed
//        onView(withText(R.string.invalid_username))
//            .inRoot(withDecorView(not(activityRule.scenario.state))).check(matches(isDisplayed()))
//    }
//}

//    @Test
//    fun testPasswordResetSuccess() {
//        // Perform actions to enter valid data and click the submit button
//        onView(withId(R.id.changePasswordId)).perform(
//            ViewActions.typeText("exampleId"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.resetForgotPassword)).perform(
//            ViewActions.typeText("ValidPassword123@"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.conformForgotPasssword)).perform(
//            ViewActions.typeText("ValidPassword123@"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.resetButton)).perform(click())
//
//        // Add assertions for the expected behavior after a successful password reset
//        onView(withId(R.id.resetForgotPassword)).check(matches(ViewMatchers.isDisplayed()))
//    }
//    // Create a custom IdlingResource for the toast
//    // val toastIdlingResource = ToastIdlingResource()
//    @Test
//    fun testPasswordResetFailure() {
//        onView(withId(R.id.changePasswordId)).perform(
//            ViewActions.typeText("exampleId"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.resetForgotPassword)).perform(
//            ViewActions.typeText("Venkk@12345"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.conformForgotPasssword)).perform(
//            ViewActions.typeText("Venu@12345"),
//            ViewActions.closeSoftKeyboard()
//        )
//        onView(withId(R.id.resetButton)).perform(click())
//
//        // Wait for the toast to appear (adjust the duration based on your app's Toast duration)
//        onView(ViewMatchers.withText("Passwords do not match")).inRoot(ToastMatcher()).check(matches(
//            ViewMatchers.isDisplayed()
//        ))
//        onView(withId(R.id.conformForgotPasssword)).check(matches(ViewMatchers.isDisplayed()))
//    }
//
//    @Test
//    fun verifyBackButtonNavigation() {
//        val activityScenario: ActivityScenario<ForgotPassword> = launch(ForgotPassword::class.java)
//        activityScenario.onActivity {
//            onView(withId(R.id.backButton)).perform(click())
//
//            val currentActivity = it.javaClass.simpleName
//            val expectedActivity = UserLoginActivity::class.java.simpleName
//
//            assertEquals(expectedActivity, currentActivity)
//        }
//    }
//
//    @Test
//    fun verifyEmptyEmailOrPhoneNumberErrorMessage() {
//        val activityScenario: ActivityScenario<ForgotPassword> = launch(ForgotPassword::class.java)
//        activityScenario.onActivity {
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//
//            onView(withId(R.id.forgotpasswordSubmit)).perform(click())
//
//            assertFalse(it.findViewById<TextInputLayout>(R.id.forgotEmail).error?.toString()?.contains("Invalid email address") ?: false)
//
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//
//            onView(withId(R.id.forgotpasswordSubmit)).perform(click())
//
//            assertFalse(it.findViewById<TextInputLayout>(R.id.forgotEmail).error?.toString()?.contains("Invalid phone number") ?: false)
//        }
//    }
//
//    @Test
//    fun verifyInvalidEmailFormatErrorMessage() {
//        val activityScenario: ActivityScenario<ForgotPassword> = launch(ForgotPassword::class.java)
//        activityScenario.onActivity {
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//            onView(withId(R.id.forgotEmail)).perform(click())
//
//            onView(withId(R.id.forgotpasswordSubmit)).perform(click())
//
//            assertTrue(it.findViewById<TextInputLayout>(R.id.forgotEmail).error?.toString()?.contains("Invalid email address") ?: false)
//        }
//    }
}
