package com.motivity.mcf_kotlin_user_management.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motivity.mcf_kotlin_user_management.R

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var login = findViewById<Button>(R.id.backToLogin)
        login.setOnClickListener{
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(this, UserLoginActivity::class.java))
                    true
                }

                R.id.bottom_maps -> {
                    // MapsActivity
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }
                R.id.bottom_search -> {
                    // MapsActivity
                    startActivity(Intent(this, UserRegistrationActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }
}