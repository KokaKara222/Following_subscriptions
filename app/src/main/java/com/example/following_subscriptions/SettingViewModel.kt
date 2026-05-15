package com.example.following_subscriptions

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class SettingViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var userName by mutableStateOf("")
    val userEmail: String
        get() = auth.currentUser?.email ?: "example@gmail.com"

    fun onNameChange(newName: String){
        userName = newName
    }

    fun signOut(){
        auth.signOut()
    }
}