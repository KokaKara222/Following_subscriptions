package com.example.following_subscriptions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscription::class], version =1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun subscriptionDao(): SubscriptionDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "subscriptiton_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}