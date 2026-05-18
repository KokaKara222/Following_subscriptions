package com.example.following_subscriptions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import com.example.following_subscriptions.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StatsViewModel (application: Application): AndroidViewModel(application ) {
    private val dao = AppDatabase.getDatabase(application).subscriptionDao()
    val subscriptions = dao.getAllSubscriptions()
    val totalExpensesFlow: Flow<Double> = subscriptions.asFlow().map{ list ->
        list.sumOf{sub ->
            sub.price.replace(",",".").toDoubleOrNull() ?: 0.0
        }
    }
}