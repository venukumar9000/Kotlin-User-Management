package com.motivity.mcf_kotlin_user_management

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GoogleSignInActivityTest {

    private lateinit var activity: GoogleSignInActivity
    private lateinit var auth: FirebaseAuth
    private lateinit var mockGoogleSignInClient: GoogleSignInClient

    @Before
    fun setup() {
        activity = GoogleSignInActivity()
        auth = Firebase.auth
        mockGoogleSignInClient = Mockito.mock(GoogleSignInClient::class.java)
        activity.googleSignInClient = mockGoogleSignInClient
    }

    @Test
    fun testCheckIfUserIsLoggedIn() {
        // User is not logged in initially
        assertEquals(false, activity.checkIfUserIsLoggedIn())

        // Log in the user
        val mockUser = Mockito.mock(FirebaseAuth::class.java)
        `when`(mockUser.currentUser).thenReturn(FirebaseAuth.getInstance().currentUser)
        assertEquals(true, activity.checkIfUserIsLoggedIn())
    }

    @Test
    fun testSignOut() {
        // Sign out the user
        activity.signOut()

        // Verify that FirebaseAuth signOut() method is called
        Mockito.verify(auth, Mockito.times(1)).signOut()

        // Verify that GoogleSignInClient signOut() method is called
        Mockito.verify(mockGoogleSignInClient, Mockito.times(1)).signOut()

        // Verify that redirectToLogin() method is called
        Mockito.verify(activity, Mockito.times(1)).redirectToLogin()
    }

//    @Test
//    fun testHandleGoogleSignInResultSuccess() {
//        val mockData = Mockito.mock(Intent::class.java)
//        val mockGoogleSignInAccount = Mockito.mock(GoogleSignInAccount::class.java)
//        val mockResult = Mockito.mock(GoogleSignInResult::class.java)
//
//        `when`(mockResult.isSuccess).thenReturn(true)
//        `when`(mockResult.signInAccount).thenReturn(mockGoogleSignInAccount)
//
//        `when`(mockData.getParcelableExtra<GoogleSignInResult>(ArgumentMatchers.anyString())).thenReturn(mockResult)
//
//        // Mock FirebaseAuth signInWithCredential() method
//        val mockAuthResult = Mockito.mock(FirebaseAuth::class.java)
//        val mockAuthTask = Mockito.mock(FirebaseAuth.class.java)
//        `when`(mockAuthResult.signInWithCredential(ArgumentMatchers.any(GoogleAuthProvider.Credential::class.java)))
//            .thenReturn(mockAuthTask)
//        `when`(mockAuthTask.addOnCompleteListener(ArgumentMatchers.any(Activity::class.java), ArgumentMatchers.any(OnCompleteListener::class.java)))
//            .thenAnswer(Answer<Any> { invocation ->
//                val listener = invocation.arguments[1] as OnCompleteListener<*>
//                listener.onComplete(null)
//                null
//            })
//
//        activity.handleGoogleSignInResult(mockData)
//
//        // Verify that FirebaseAuth signInWithCredential() method is called
//        Mockito.verify(auth, Mockito.times(1))
//            .signInWithCredential(ArgumentMatchers.any(GoogleAuthProvider.Credential::class.java))
//    }
//
//    @Test
//    fun testHandleGoogleSignInResultFailure() {
//        val mockData = Mockito.mock(Intent::class.java)
//        val mockResult = Mockito.mock(GoogleSignInResult::class.java)
//
//        `when`(mockResult.isSuccess).thenReturn(false)
//
//        `when`(mockData.getParcelableExtra<GoogleSignInResult>(ArgumentMatchers.anyString())).thenReturn(mockResult)
//
//        activity.handleGoogleSignInResult(mockData)
//
//        // Verify that FirebaseAuth signInWithCredential() method is NOT called when the result is not successful
//        Mockito.verify(auth, Mockito.times(0))
//            .signInWithCredential(ArgumentMatchers.any(GoogleAuthProvider.Credential::class.java))
//    }

//    @Test
//    fun testFirebaseAuthWithGoogle() {
//        val mockIdToken = "mockIdToken"
//        val mockCredential = Mockito.mock(GoogleAuthProvider.Credential::class.java)
//        val mockAuthResult = Mockito.mock(FirebaseAuth::class.java)
//        val mockAuthTask = Mockito.mock(FirebaseAuth.class.java)
//
//        `when`(mockAuthResult.signInWithCredential(ArgumentMatchers.any(GoogleAuthProvider.Credential::class.java)))
//            .thenReturn(mockAuthTask)
//        `when`(mockAuthTask.addOnCompleteListener(ArgumentMatchers.any(Activity::class.java), ArgumentMatchers.any(OnCompleteListener::class.java)))
//            .thenAnswer(Answer<Any> { invocation ->
//                val listener = invocation.arguments[1] as OnCompleteListener<*>
//                listener.onComplete(null)
//                null
//            })
//
//        activity.firebaseAuthWithGoogle(mockIdToken)
//
//        // Verify that FirebaseAuth signInWithCredential() method is called
//        Mockito.verify(auth, Mockito.times(1))
//            .signInWithCredential(ArgumentMatchers.any(GoogleAuthProvider.Credential::class.java))
//    }

    @Test
    fun testSignInWithGoogle() {
        val mockSignInIntent = Mockito.mock(Intent::class.java)

        // Mock GoogleSignInClient signInIntent
        `when`(mockGoogleSignInClient.signInIntent).thenReturn(mockSignInIntent)

        activity.signInWithGoogle()

        // Verify that GoogleSignInClient signInIntent is called
        Mockito.verify(mockGoogleSignInClient, Mockito.times(1)).signInIntent
    }
}
