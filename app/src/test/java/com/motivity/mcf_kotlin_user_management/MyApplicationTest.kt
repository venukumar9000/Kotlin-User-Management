package com.motivity.mcf_kotlin_user_management
import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.motivity.mcf_kotlin_user_management.models.MyApplication
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MyApplicationTest {

    @Mock
    private lateinit var mockApplication: Application

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testOnCreate() {
        // Mock the required FacebookSdk and AppEventsLogger calls
        mockStatic(FacebookSdk::class.java)
        mockStatic(AppEventsLogger::class.java)

        // Call the method you want to test
       // MyApplication().onCreate()

//        // Verify that the expected methods are called
//        verifyStatic(FacebookSdk::class.java)
//        FacebookSdk.sdkInitialize(any())
//
//        verifyStatic(AppEventsLogger::class.java)
//        AppEventsLogger.activateApp(any())
    }
}
