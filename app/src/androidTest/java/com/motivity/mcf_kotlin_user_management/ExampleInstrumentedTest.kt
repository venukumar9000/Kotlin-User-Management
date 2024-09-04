package com.motivity.mcf_kotlin_user_management

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.motivity.mcf_kotlin_user_management", appContext.packageName)
    }

//    @Test
//    fun testHandler() {
//
//        getInstrumentation().runOnMainSync(Runnable {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            // Here you can call methods which have Handler
//        })
//    }
}