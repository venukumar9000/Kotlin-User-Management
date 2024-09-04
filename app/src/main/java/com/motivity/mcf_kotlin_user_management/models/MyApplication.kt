package com.motivity.mcf_kotlin_user_management.models

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}