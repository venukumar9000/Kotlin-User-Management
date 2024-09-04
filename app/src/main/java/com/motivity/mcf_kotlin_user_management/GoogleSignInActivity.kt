package com.motivity.mcf_kotlin_user_management

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.motivity.mcf_kotlin_user_management.activities.UserLoginActivity
import com.motivity.mcf_kotlin_user_management.databinding.ActivityGoogleSignInBinding

class GoogleSignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoogleSignInBinding
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val isLoggedIn = checkIfUserIsLoggedIn()

        if (isLoggedIn) {
            val displayName = auth.currentUser?.displayName ?: "User"
            binding.textDisplayName.text = "Welcome, $displayName"
            binding.logout.visibility = View.VISIBLE
        } else {
            // Handle case when user is not logged in
            binding.textDisplayName.text = "Please log in"
            binding.logout.visibility = View.GONE
        }

        binding.logout.setOnClickListener {
            signOut()
        }



        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun checkIfUserIsLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            redirectToLogin()
        }
    }

    fun redirectToLogin() {
        val loginIntent = Intent(applicationContext, UserLoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.result
            val idToken = account?.idToken
            firebaseAuthWithGoogle(idToken)
        } catch (e: Exception) {
            // Handle exceptions
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val displayName = user?.displayName ?: "User"
                    binding.textDisplayName.text = "Welcome, $displayName"
                    binding.logout.visibility = View.VISIBLE
                } else {
                    // If sign in fails, display a message to the user.
                    // Handle exceptions
                }
            }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            // Result returned from launching the Intent from GoogleSignInClient.
            if (resultCode == RESULT_OK) {
                handleGoogleSignInResult(data)
            }
        }
    }
}
