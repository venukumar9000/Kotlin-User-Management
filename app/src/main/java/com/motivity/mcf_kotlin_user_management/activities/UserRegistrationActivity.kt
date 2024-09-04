package com.motivity.mcf_kotlin_user_management.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegistrationActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        val backbutton =findViewById<ImageButton>(R.id.backButton)
        backbutton.setOnClickListener{
            var intent =Intent(this,UserLoginActivity::class.java)
            startActivity(intent)
        }

        // Initialize UI components
        val firstNameTextInputLayout = findViewById<TextInputLayout>(R.id.fname)
        val lastNameTextInputLayout = findViewById<TextInputLayout>(R.id.lname)
        val emailTextInputLayout = findViewById<TextInputLayout>(R.id.email)
        val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.password)
        val contactNumberTextInputLayout = findViewById<TextInputLayout>(R.id.phoneNumber)
        val buttonSignUp = findViewById<Button>(R.id.SignUpRegister)

        // Add TextWatchers to clear errors on text change
        setupTextWatcher(firstNameTextInputLayout)
        setupTextWatcher(lastNameTextInputLayout)
        setupTextWatcher(emailTextInputLayout)
        setupTextWatcher(passwordTextInputLayout)
        setupTextWatcher(contactNumberTextInputLayout)

        // Set OnClickListener for SignUp button
        buttonSignUp.setOnClickListener {

            val firstName = firstNameTextInputLayout.editText?.text.toString().trim()
            val lastName = lastNameTextInputLayout.editText?.text.toString().trim()
            val email = emailTextInputLayout.editText?.text.toString().trim()
            val password = passwordTextInputLayout.editText?.text.toString().trim()
            val contactNumber = contactNumberTextInputLayout.editText?.text.toString().trim()
            val countryCode = "+91"
            val role = "USER"

            // Validate all fields are filled
            if (!isValidField(firstName, firstNameTextInputLayout, "First name") ||
                !isValidField(lastName, lastNameTextInputLayout, "Last name") ||
                !isValidField(email, emailTextInputLayout, "Email") ||
                !isValidField(password, passwordTextInputLayout, "Password") ||
                !isValidField(contactNumber, contactNumberTextInputLayout, "Phone number")
            ) {
                return@setOnClickListener
            }

            // Validate email
            if (!isValidEmail(email, emailTextInputLayout)) {
                return@setOnClickListener
            }

            // Validate password
            if (!isValidPassword(password, passwordTextInputLayout)) {
                return@setOnClickListener
            }

            // Validate phone number
            if (!isValidPhoneNumber(contactNumber, contactNumberTextInputLayout)) {
                return@setOnClickListener
            }

            // Create user registration object
            val userRegistration =
                UserRegistration(firstName, lastName, email, password, contactNumber, countryCode, role)

            // Make API call for user registration
            RetrofitClient.instance.createUser(userRegistration, "application/json")
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // Handle API call failure
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        // Handle API call response
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                response.body().toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(applicationContext, Dashboard::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Registration failed.",
                                Toast.LENGTH_LONG
                            ).show()
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

    // Set up TextWatcher to clear errors on text change
    private fun setupTextWatcher(textInputLayout: TextInputLayout) {
        textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                // Clear error on text change
                textInputLayout.error = null
            }

            override fun afterTextChanged(editable: Editable) {
                // Do nothing
            }
        })
    }

    // Validate if a field is empty and set error if needed
    private fun isValidField(
        value: String,
        textInputLayout: TextInputLayout,
        fieldName: String
    ): Boolean {
        if (value.isEmpty()) {
            textInputLayout.error = "$fieldName is required"
            return false
        }
        return true
    }

    // Validate email format and set error if needed
    private fun isValidEmail(email: String, emailTextInputLayout: TextInputLayout): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return if (!email.matches(emailRegex.toRegex())) {
            emailTextInputLayout.error = "Invalid email address"
            false
        } else {
            emailTextInputLayout.error = null // Clear error
            true
        }
    }

    // Validate password format and set error if needed
    private fun isValidPassword(
        password: String,
        passwordTextInputLayout: TextInputLayout
    ): Boolean {
        val passwordRegex =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        return if (!password.matches(passwordRegex.toRegex())) {
            passwordTextInputLayout.error =
                "Password must be at least 8 characters, including one symbol, one uppercase letter, and one digit."
            false
        } else {
            passwordTextInputLayout.error = null // Clear error
            true
        }
    }

    // Validate phone number format and set error if needed
    private fun isValidPhoneNumber(
        phoneNumber: String,
        contactNumberTextInputLayout: TextInputLayout
    ): Boolean {
        return if (phoneNumber.length != 10 || !phoneNumber.all { it.isDigit() }) {
            contactNumberTextInputLayout.error = "Invalid phone number. It should have 10 digits."
            false
        } else {
            contactNumberTextInputLayout.error = null // Clear error
            true
        }
    }


    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, Dashboard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}


