package com.ucsm.campusmarket.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    onResult(true, null)

                } else {

                    it.exception?.printStackTrace()
                    Log.e("FIREBASE_LOGIN", "Error", it.exception)

                    onResult(false, it.exception.toString())
                }

            }

    }

    fun register(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    onResult(true, null)

                } else {

                    it.exception?.printStackTrace()
                    Log.e("FIREBASE_REGISTER", "Error", it.exception)

                    onResult(false, it.exception.toString())
                }

            }

    }

    fun logout() {
        auth.signOut()
    }

    fun isLogged(): Boolean {
        return auth.currentUser != null
    }
}