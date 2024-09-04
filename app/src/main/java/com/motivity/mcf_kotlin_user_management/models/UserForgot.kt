package com.motivity.mcf_kotlin_user_management.models

data class UserForgot(   val email:String,
                         val changePasswordId:String,
                         val newPassword:String
)
