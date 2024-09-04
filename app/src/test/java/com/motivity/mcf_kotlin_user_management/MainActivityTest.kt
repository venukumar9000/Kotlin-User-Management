package com.motivity.mcf_kotlin_user_management
//
//import org.junit.jupiter.api.Assertions.*
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
//import junit.framework.Assert.assertEquals
//import junit.framework.Assert.assertNotNull
//import org.junit.Test
//import org.mockito.Mockito.`mock`
//import org.mockito.Mockito.verify
//
//
//class MainActivityTest{
//    @Test
//    fun testBottomNavigationViewInitialization() {
//        val mainActivity = MainActivity()
//        assertNotNull(mainActivity.bottomNavigationView)
//    }
//    @Test
//    fun testHandleBottomNavigationItemSelected() {
//        val mainActivity = MainActivity()
//        val bottomNavigationItemSelectedListener = mainActivity.bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.bottom_home -> {
//                    mainActivity.replaceActivity(UserLoginActivity::class.java)
//                    true
//                }
//                R.id.bottom_search -> {
//                    mainActivity.replaceActivity(UserRegistrationActivity::class.java)
//                    true
//                }
//                R.id.bottom_maps -> {
//                    mainActivity.replaceActivity(ForgotPassword::class.java)
//                    true
//                }
//                R.id.bottom_settings -> {
//                    mainActivity.replaceActivity(ResetPassword::class.java)
//                    true
//                }
//                else -> false
//            }
//        }
//
//        // Verify that the listener is called when an item is selected
//        val homeMenuItem = mainActivity.bottomNavigationView.menu.findItem(R.id.bottom_home)
//        bottomNavigationItemSelectedListener.onNavigationItemSelected(homeMenuItem)
//        val searchMenuItem = mainActivity.bottomNavigationView.menu.findItem(R.id.bottom_search)
//        bottomNavigationItemSelectedListener.onNavigationItemSelected(searchMenuItem)
//        val mapsMenuItem = mainActivity.bottomNavigationView.menu.findItem(R.id.bottom_maps)
//        bottomNavigationItemSelectedListener.onNavigationItemSelected(mapsMenuItem)
//        val settingsMenuItem = mainActivity.bottomNavigationView.menu.findItem(R.id.bottom_settings)
//        bottomNavigationItemSelectedListener.onNavigationItemSelected(settingsMenuItem)
//
//        // Verify that the correct activity is replaced when an item is selected
//        assertEquals(UserLoginActivity::class.java, mainActivity.currentActivity)
//        assertEquals(UserRegistrationActivity::class.java, mainActivity.currentActivity)
//        assertEquals(ForgotPassword::class.java, mainActivity.currentActivity)
//        assertEquals(ResetPassword::class.java, mainActivity.currentActivity)
//    }
//
//
//}
//import android.content.Intent
//import androidx.test.core.app.ActivityScenario
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import com.motivity.mcf_kotlin_user_management.activities.ForgotPassword
//import com.motivity.mcf_kotlin_user_management.activities.ResetPassword
//import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
//import com.motivity.mcf_kotlin_user_management.activities.UserRegistrationActivity
//import org.junit.Test
//
//class MainActivityTest {
//
//    @Test
//    fun testBottomNavigationViewInitialization() {
//        // Start the MainActivity
//        ActivityScenario.launch(MainActivity::class.java).use {
//            // Check if BottomNavigationView is initialized
//            onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
//        }
//    }
//
//    @Test
//    fun testHandleBottomNavigationItemSelected() {
//        // Start the MainActivity
//        ActivityScenario.launch(MainActivity::class.java).use {
//            // Click on different BottomNavigationView items
//            onView(withId(R.id.bottom_home)).perform(click())
//            onView(withId(R.id.bottom_search)).perform(click())
//            onView(withId(R.id.bottom_maps)).perform(click())
//            onView(withId(R.id.bottom_settings)).perform(click())
//        }
//    }
//
//    @Test
//    fun testReplaceActivity() {
//        // Start the MainActivity
//        ActivityScenario.launch(MainActivity::class.java).use {
//            // Replace the activity with UserLoginActivity
//            it.onActivity { activity ->
//                activity.replaceActivity(UserLoginActivity::class.java)
//            }
//
//            // Verify that the current activity is UserLoginActivity
//            onView(withId(R.id.text_display_name)).check(matches(withText("Welcome, User")))
//        }
//    }
//}
