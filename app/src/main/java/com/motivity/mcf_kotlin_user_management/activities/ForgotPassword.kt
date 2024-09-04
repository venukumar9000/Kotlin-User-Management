package com.motivity.mcf_kotlin_user_management.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password3)
        val backbutton = findViewById<ImageButton>(R.id.backButton)
        backbutton.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        // Finding views by their IDs
        val loginIdTextInputLayout = findViewById<TextInputLayout>(R.id.forgotEmail)
        val phoneIdTextInputLayout = findViewById<TextInputLayout>(R.id.forgotEmail)
        val buttonSubmit = findViewById<Button>(R.id.forgotpasswordSubmit)

        // Setting click listener for the submit button
        buttonSubmit.setOnClickListener {
            // Retrieving the entered login ID (email)
            val loginId = loginIdTextInputLayout.editText?.text.toString().trim()
            val toPhoneNumber = "+91" + phoneIdTextInputLayout.editText?.text.toString().trim()

            // Validate email and phone number
            if (!isValidEmail(loginId) && !isValidPhone(toPhoneNumber)) {
                loginIdTextInputLayout.error = "Invalid email address"
                phoneIdTextInputLayout.error = "Invalid phone number"
                return@setOnClickListener
            } else {
                // Clear the error if the email and phone number are valid
                loginIdTextInputLayout.error = null
                phoneIdTextInputLayout.error = null
            }

            // Check if the entered data is an email
            if (isValidEmail(loginId)) {
                // Making a network request to initiate the password reset process for email
                RetrofitClient.instance.forgotPassword(loginId, "application/json")
                    .enqueue(object : Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            // Handle failure, show error message
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            // Handle the server response
                            if (response.isSuccessful) {
                                // If the response is successful, show a success message
                                val responseString = response.body()
                                Toast.makeText(applicationContext, responseString, Toast.LENGTH_LONG)
                                    .show()

                                // Navigate to the ResetPassword activity for email
                                val intent = Intent(applicationContext, ResetPassword::class.java)
                                intent.putExtra("loginId", loginId)
                                startActivity(intent)
                            }
                        }
                    })
            } else if (isValidPhone(toPhoneNumber)) {
                // Making a network request to initiate the password reset process for phone number
                RetrofitClient.instance.sendOtp(toPhoneNumber, "application/json")
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            // Handle the server response
                            if (response.isSuccessful) {
                                // If the response is successful, show a success message
                                val responseString = response.body()
                                Toast.makeText(applicationContext, responseString, Toast.LENGTH_LONG)
                                    .show()

                                // Navigate to the VerifyOtp activity for phone number
                                val intent = Intent(applicationContext, VerifyOtp::class.java)
                                intent.putExtra("toPhoneNumber", toPhoneNumber)
                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            // Handle failure, show error message
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
            } else {
                // If the response is not successful, show an error message
                Toast.makeText(
                    applicationContext,
                    "Failed to process request.",
                    Toast.LENGTH_LONG
                ).show()
            }
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

    override fun onStart() {
        super.onStart()

        // Check if the user is already logged in, if yes, redirect to the Dashboard
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, Dashboard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    /**
     * Validates the email address.
     * @return True if the email is valid, false otherwise.
     */
     fun isValidEmail(email: String): Boolean {
        // Add your email validation logic here
        return email.contains("@") && email.endsWith(".com")
    }

    /**
     * Validates the phone number.
     * @return True if the phone number is valid, false otherwise.
     */
     fun isValidPhone(toPhoneNumber: String): Boolean {
        // Add your phone number validation logic here
        // For simplicity, you can check if the phone number has exactly 10 digits
        return toPhoneNumber.matches(Regex("\\+91\\d{10}"))
    }
}
