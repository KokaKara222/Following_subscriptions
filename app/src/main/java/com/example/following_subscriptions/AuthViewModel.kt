package com.example.following_subscriptions

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var confirmPassword by mutableStateOf("")

    fun signIn(onSuccess: () -> Unit) {
        if (!validateFields(isSingUp = false)) return

        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            isLoading = false
            if (task.isSuccessful) {
                onSuccess()
            } else {
                errorMessage = task.exception?.localizedMessage ?: "Ошибка входа"
            }
        }
    }


    fun signUp(onSuccess: () -> Unit) {
        if (!validateFields(isSingUp = false)) return

        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = task.exception?.localizedMessage ?: "Ошибка регистрации"
                }
            }
    }

    private fun validateFields(isSingUp: Boolean): Boolean {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Заполните все поля"
            return false
        }
        if (password.length < 6) {
            errorMessage = "Пароль должен быть не менее 6 символов"
            return false
        }
        if (isSingUp && password != confirmPassword) {
            errorMessage = "Пароли не совпадают"
            return false
        }
        return true
    }

    fun clearError() {
        errorMessage = null
    }
}