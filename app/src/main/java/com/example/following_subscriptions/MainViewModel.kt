package com.example.following_subscriptions

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
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
            val updatedSub = subscription.copy(isActive = isActive)
            dao.updateSubscription(updatedSub)

            if(isActive){
                scheduleNotification(updatedSub)
            }else{
                cancelNotification(subscription.id)
            }
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
                dao.updateSubscription(updateSub)

                cancelNotification(updateSub.id)
                scheduleNotification(updateSub)
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
                val newId = dao.insertSubscription(newSub)
                val subWithId = newSub.copy(id = newId)
                scheduleNotification(subWithId)
            }
            launch(Dispatchers.Main){
                closeSheet()
            }
        }
    }

    fun deleteSubscription(subscription: Subscription){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteSubscription(subscription)
            cancelNotification(subscription.id)
        }
    }


    private fun scheduleNotification(subscription: Subscription){
        if (!subscription.isActive) return
        try{
            val context = getApplication<Application>().applicationContext
            val sdf = SimpleDateFormat("dd MM yyyy", Locale("ru"))
            val subDate = sdf.parse(subscription.date) ?: return

            val calendar = Calendar.getInstance().apply {
                time = subDate
                add(Calendar.DAY_OF_YEAR, -1)
                set(Calendar.HOUR_OF_DAY,12)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
            val delay = calendar.timeInMillis - System.currentTimeMillis()

            if (delay > 0){
                val data = workDataOf(
                        "SUB_NAME" to subscription.name,
                        "SUB_PRICE" to subscription.price
                )
                val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInputData(data)
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .addTag("sub_${subscription.id}")
                    .build()
                WorkManager.getInstance(context).enqueue(workRequest)
            }
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    private fun cancelNotification(subscriptionId: Long){
        val context = getApplication<Application>().applicationContext
        WorkManager.getInstance(context).cancelAllWorkByTag("sub_$subscriptionId")
    }
}

