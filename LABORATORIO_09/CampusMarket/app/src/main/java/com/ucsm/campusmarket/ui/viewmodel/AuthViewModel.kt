package com.ucsm.campusmarket.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ucsm.campusmarket.data.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var error = mutableStateOf<String?>(null)
        private set

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        repository.login(email, password) { ok, message ->
            if (ok) {
                error.value = null
                onSuccess()
            } else {
                error.value = message
            }
        }
    }

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        repository.register(email, password) { ok, message ->
            if (ok) {
                error.value = null
                onSuccess()
            } else {
                error.value = message
            }
        }
    }

    fun isLogged() = repository.isLogged()

    // 🔥 FIX IMPORTANTE: ahora sí hace logout y notifica UI
    fun logout(onLogout: () -> Unit) {
        repository.logout()
        onLogout()
    }
}