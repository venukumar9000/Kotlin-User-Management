package com.motivity.mcf_kotlin_user_management.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOtp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        val backbutton = findViewById<ImageButton>(R.id.backButton)
        backbutton.setOnClickListener {
            var intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        val otp = findViewById<TextView>(R.id.otpEditText)
        val submitBtn = findViewById<Button>(R.id.verifyOtpSubmit)

        submitBtn.setOnClickListener {
            val toPhoneNumber = intent.getStringExtra("toPhoneNumber").toString()
            val sentOtp = otp.text.toString().trim()

            // Validate OTP
            if (isValidOtp(sentOtp)) {
                // Making a network request to verify the OTP
                RetrofitClient.instance.verifyOtp(toPhoneNumber, sentOtp, "application/json")
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            // Handle the server response
                            if (response.isSuccessful) {
                                // If the response is successful, show a success message
                                val responseString = response.body()
                                Toast.makeText(applicationContext, responseString, Toast.LENGTH_LONG)
                                    .show()

                                // Navigate to the ResetPassword activity
                                val intent = Intent(applicationContext, Dashboard::class.java)
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
                // Show an error message for invalid OTP
                Toast.makeText(applicationContext, "Invalid OTP. Please enter a 6-digit number.", Toast.LENGTH_LONG).show()
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

    /**
     * Validates the OTP.
     * @return True if the OTP is valid, false otherwise.
     */
    private fun isValidOtp(otp: String): Boolean {
        // Add your OTP validation logic here
        // For simplicity, you can check if the OTP has exactly 6 digits and contains only numbers
        return otp.matches(Regex("\\d{6}"))
    }
}
