package com.motivity.mcf_kotlin_user_management.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.motivity.mcf_kotlin_user_management.GoogleSignInActivity
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.TwitterActivity
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.LoginResponse
import com.motivity.mcf_kotlin_user_management.models.UserLogin
import com.motivity.mcf_kotlin_user_management.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays

open class UserLoginActivity : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        FacebookSdk.sdkInitialize(applicationContext)


        var signUp = findViewById<Button>(R.id.SignUp)
        signUp.setOnClickListener {
            // Define the intent to navigate to the second activity
            val intent = Intent(this, UserRegistrationActivity::class.java)
            startActivity(intent)
        }

        var forgotButton = findViewById<TextView>(R.id.forgotpassword)
        forgotButton.setOnClickListener(){
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        var buttonLogin = findViewById<Button>(R.id.login)
        var editTextEmail = findViewById<TextView>(R.id.username)
        var editTextPassword = findViewById<TextView>(R.id.password)
        var twitterLoginButton = findViewById<Button>(R.id.twitterLoginButton)

        twitterLoginButton.setOnClickListener{
            val intent = Intent(applicationContext, TwitterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isEmpty()) {
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                return@setOnClickListener
            }
            val userLogin = UserLogin(email, password)

            RetrofitClient.instance.userLogin(userLogin, "application/json")
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse?.accessToken != null) {
                                // HTTP status code is 200, save the accessToken to SharedPrefs

                                SharedPrefManager.getInstance(applicationContext).saveUser(loginResponse)

                                // Start the Profile activity
                                val intent = Intent(applicationContext, Dashboard::class.java)
                                startActivity(intent)
                            } else {
                                // Handle the case when response.body() is null
                                Toast.makeText(
                                    applicationContext,
                                    "Response body is null",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // Handle HTTP error cases
                            val errorMessage = when (response.code()) {
                                401 -> "Unauthorized" // You can add more cases for specific status codes
                                else -> "HTTP Error: ${response.code()}"
                            }
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
        }


    }


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (!email.matches(emailRegex.toRegex())) {
            Toast.makeText(
                applicationContext,
                "Invalid email address. Please enter a valid email address.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        if (!password.matches(passwordRegex.toRegex())) {
            Toast.makeText(
                applicationContext,
                "Invalid password. Password must contain at least 8 characters, including one symbol, one uppercase letter, and one digit.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, UserRegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            // Define the intent to navigate to the second activity
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }
        // google signin
        auth = Firebase.auth

        // Configure Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Replace with your Web Client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val googlesign = findViewById<Button>(R.id.btn_sign_in_google)
        googlesign.setOnClickListener {
            signIn()
        }

        var btnLoginFacebook = findViewById<Button>(R.id.facebookLoginButton)

        btnLoginFacebook.setOnClickListener(View.OnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("public_profile", "email")
            )
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, AuthenticatedActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")
                    }
                })
        })
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == UserLoginActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(applicationContext, GoogleSignInActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA_NAME"
    }
}
