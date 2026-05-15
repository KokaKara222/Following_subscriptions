package com.example.following_subscriptions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.following_subscriptions.data.AppDatabase
import com.example.following_subscriptions.data.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.toArgb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).subscriptionDao()
    val subscriptions = dao.getAllSubscriptions()
    var showSheet by mutableStateOf(false)
        private set

    fun openSheet(){ showSheet= true}
    fun closeSheet(){ showSheet = false}
    fun addSubscription(name: String, category: String, price: String, date: String, icon: Int, color:Color) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSub = Subscription(
                name = name,
                category = category,
                date = date,
                price = price,
                iconRes = icon,
                colorInt= color.toArgb()
            )
            dao.insertSubscription(newSub)

            launch(Dispatchers.Main){
                closeSheet()
            }
        }
    }



}

