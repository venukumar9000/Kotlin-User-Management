package com.motivity.mcf_kotlin_user_management.models

data class UserRegistration(val firstName:String,
                            val lastName:String,
                            val emailAddress:String,
                            val password:String,
                            val contactNumber:String,
                            val countryCode:String,
                            val role:String)
