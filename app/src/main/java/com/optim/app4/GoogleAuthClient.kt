package com.optim.app4

import androidx.activity.ComponentActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

class GoogleAuthClient(
    private val activity: ComponentActivity,

    ) {

    private val tag = "GoogleSignInClient: "
    private val credentialManager = CredentialManager.create(activity)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            println(tag + "already sign in")
            return true
        }
        return false
    }

    suspend fun signIn(): Boolean {
        if (isSignIn()){
            return true
        }
        try {
            val result = buildCredentialRequest()
            return handleSignIn(result)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "signIn error $${e.message}")
            return false
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential
        if (
            credential  is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ){
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                println(tag + "name ${tokenCredential.displayName}")
                println(tag + "id ${tokenCredential.id}")

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException) {
                println(tag + "GoogleIdTokenParsingException: ${e.message}")
                return false
            }
        }else {
            println(tag + "credential is not GoogleIdTokenCredential")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val serverClientId = activity.getString(R.string.default_web_client_id)

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(serverClientId)
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            request = request,
            context = activity
        )
    }


    suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        firebaseAuth.signOut()
    }
}