package com.motivity.mcf_kotlin_user_management.storage

import android.content.Context
import com.motivity.mcf_kotlin_user_management.models.LoginResponse

class SharedPrefManager
private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

//    val user: User
//        get() {
//            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//            return User(
//                    sharedPreferences.getInt("id", -1),
//                    sharedPreferences.getString("email",null) ,
//                    sharedPreferences.getString("name", null),
//                    sharedPreferences.getString("school", null)
//            )
//        }


    fun saveUser(loginResponse: LoginResponse) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("accessToken", loginResponse.accessToken)
        editor.putString("email", loginResponse.email)
        editor.putString("refreshToken", loginResponse.refreshToken)
        editor.putString("userName", loginResponse.userName)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}