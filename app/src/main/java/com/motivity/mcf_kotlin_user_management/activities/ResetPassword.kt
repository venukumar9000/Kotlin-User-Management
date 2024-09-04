package com.motivity.mcf_kotlin_user_management.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.UserForgot
import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        val backbutton = findViewById<ImageButton>(R.id.backButton)
        backbutton.setOnClickListener{
            var intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

        // Finding views by their IDs
        val changePasswordIdTextView = findViewById<TextView>(R.id.changePasswordId)
        val resetPasswordTextView = findViewById<TextView>(R.id.resetForgotPassword)
        val confirmPasswordTextView = findViewById<TextView>(R.id.conformForgotPasssword)
        val buttonSubmit = findViewById<Button>(R.id.resetButton)
      //  val email = intent.getStringExtra("loginId")

        // Setting click listener for the submit button
        buttonSubmit.setOnClickListener {
            // Retrieving values from the input fields
            val email = intent.getStringExtra("loginId").toString()
            val changePasswordId = changePasswordIdTextView.text.toString()
            val resetPassword = resetPasswordTextView.text.toString().trim()
            val confirmPassword = confirmPasswordTextView.text.toString().trim()



            // Validate all input fields
            if (!isValidInputFields(changePasswordId, resetPassword, confirmPassword)) {
                return@setOnClickListener
            }

            // Creating UserForgot object
            val userForgot = UserForgot(email, changePasswordId, resetPassword)

            // Making a network request to reset the password
            RetrofitClient.instance.resetPassword(userForgot, "application/json")
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
                            Toast.makeText(applicationContext, responseString, Toast.LENGTH_LONG).show()

                            // Navigate to the Dashboard activity
                            val intent = Intent(applicationContext, Dashboard::class.java)
                            startActivity(intent)
                        } else {
                            // If the response is not successful, show an error message
                            Toast.makeText(applicationContext, "Failed to process request.Invalid code", Toast.LENGTH_LONG).show()
                        }
                    }
                })
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
     * Validates the password.
     * @return True if the password is valid, false otherwise.
     */
    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()

        return if (passwordPattern.matches(password)) {
            true
        } else {
            Toast.makeText(
                applicationContext,
                "Invalid password. Password must contain at least 8 characters, including at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)",
                Toast.LENGTH_LONG
            ).show()
            false
        }
    }

    /**
     * Validates all input fields.
     * @return True if all fields are valid, false otherwise.
     */
    fun isValidInputFields(
        changePasswordId: String,
        resetPassword: String,
        confirmPassword: String
    ): Boolean {
        // Check if any field is empty
        if (changePasswordId.isEmpty() || resetPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_LONG).show()
            return false
        }

        // Validate password
        if (!isValidPassword(resetPassword)) {
            return false
        }

        // Check if password and confirm password match
        if (resetPassword != confirmPassword) {
            Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }



}
