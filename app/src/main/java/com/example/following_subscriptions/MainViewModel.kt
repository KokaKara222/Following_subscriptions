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

    var editingSubscription by mutableStateOf<Subscription?>(null)
        private set
    val totalExpenses: LiveData<Double> = subscriptions.map{ list ->
        list.filter{it.isActive}.sumOf {sub->
            calculateMonthlyPrice(sub.price, sub.period)
        }
    }

    private fun calculateMonthlyPrice(priceStr: String, period: String): Double {
        val price = priceStr.replace(",", ".").toDoubleOrNull() ?: 0.0
        return when (period.lowercase().trim()) {
            "3 месяца" -> price / 3.0
            "год", "1 год" -> price / 12.0
            else -> price
        }
    }

    fun toggleSubscriptionActive(subscription: Subscription, isActive: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val updatedSub = Subscription(
                id = subscription.id,
                name = subscription.name,
                category = subscription.category,
                date = subscription.date,
                price = subscription.price,
                period = subscription.period,
                iconRes = subscription.iconRes,
                imageUri = subscription.imageUri,
                colorInt = subscription.colorInt,
                isActive = isActive
            )
            dao.updateSubscription(updatedSub)
        }
    }
    fun openSheet(){
        editingSubscription = null
        showSheet= true}

    fun openSheetForEdit(subscription: Subscription){
        editingSubscription =subscription
        showSheet =true
    }
    fun closeSheet(){
        showSheet = false
        editingSubscription = null
    }

    fun saveSubscription(
        name: String,
        category: String,
        price: String,
        date: String,
        period: String,
        icon: Int,
        imageUri:String?,
        color:Color
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentEdit = editingSubscription
            if (currentEdit != null) {
                val updateSub = currentEdit.copy(
                    name = name,
                    category = category,
                    date = date,
                    price = price,
                    period = period,
                    iconRes = icon,
                    imageUri = imageUri,
                    colorInt = color.toArgb(),
                )
                dao.insertSubscription(updateSub)
            } else{
                val newSub = Subscription(
                    name = name,
                    category = category,
                    date = date,
                    price = price,
                    period = period,
                    iconRes = icon,
                    imageUri = imageUri,
                    colorInt= color.toArgb(),
                    isActive = true
                )
                dao.insertSubscription(newSub)
            }

            launch(Dispatchers.Main){
                closeSheet()
            }
        }
    }

    fun deleteSubscription(subscription: Subscription){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteSubscription(subscription)
        }
    }


}

