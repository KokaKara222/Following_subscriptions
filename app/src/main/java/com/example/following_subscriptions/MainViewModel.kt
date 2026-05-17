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
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).subscriptionDao()
    val subscriptions: LiveData<List<Subscription>> = dao.getAllSubscriptions()
    var showSheet by mutableStateOf(false)
        private set

    val totalExpenses: Double
        get()= subscriptions.value?.sumOf {sub ->
        sub.price.replace(",", ".").toDoubleOrNull() ?: 0.0
    } ?: 0.0

    fun openSheet(){ showSheet= true}
    fun closeSheet(){ showSheet = false}
    fun addSubscription(
        name: String,
        category: String,
        price: String,
        date: String,
        period: String,
        icon: Int,
        imageUri:String?,
        color:Color) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSub = Subscription(
                name = name,
                category = category,
                date = date,
                price = price,
                period = period,
                iconRes = icon,
                imageUri = imageUri,
                colorInt= color.toArgb()
            )
            dao.insertSubscription(newSub)

            launch(Dispatchers.Main){
                closeSheet()
            }
        }
    }
}

