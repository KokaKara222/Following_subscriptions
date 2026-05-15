package com.example.following_subscriptions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val rsubscriptions =  mutableStateListOf<Subscription>()
    val subscriptions: List<Subscription> get() = subscriptions
    var showSheet by  mutableStateOf(false)
        private set
    fun openSheet(){
        showSheet = true
    }

    fun closeSheet(){
        showSheet= false
    }

    fun addSubscription(name: String, category: String, price: String, date: String, icon: Int, color:androidx.compose.ui.graphics.Color){
        rsubscriptions.add(
            Subscription(
                id = rsubscriptions.size,
                name =name,
                category = category,
                date= date,
                price =price,
                iconRes = icon,
                color =color
            )
        )
        closeSheet()
    }
}

