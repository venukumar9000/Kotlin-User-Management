package com.motivity.mcf_kotlin_user_management.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("accessToken"  ) var accessToken  : String? = null,
                         @SerializedName("email"        ) var email        : String? = null,
                         @SerializedName("refreshToken" ) var refreshToken : String? = null,
                         @SerializedName("userName"     ) var userName     : String? = null,)
