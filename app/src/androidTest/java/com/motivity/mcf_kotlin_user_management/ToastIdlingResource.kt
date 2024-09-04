package com.motivity.mcf_kotlin_user_management
import android.os.Handler
import android.os.Looper
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class ToastIdlingResource : IdlingResource {

    private val isIdleNow = AtomicBoolean(true)
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return ToastIdlingResource::class.java.name
    }

    override fun isIdleNow(): Boolean {
        return isIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    fun notifyIdle() {
        if (!isIdleNow.getAndSet(true)) {
            callback?.onTransitionToIdle()
        }
    }

    fun notifyBusy() {
        if (isIdleNow.getAndSet(false)) {
            // No transition callback since we are already busy
        }
    }
}
