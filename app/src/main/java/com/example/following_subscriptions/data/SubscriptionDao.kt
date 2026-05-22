package com.example.following_subscriptions.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SubscriptionDao{
    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptions(): LiveData<List<Subscription>>

    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptionsFlow(): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription)

    @Update
    suspend fun updateSubscription(subscription: Subscription)
    @Delete
    suspend fun deleteSubscription(subscription: Subscription)
}