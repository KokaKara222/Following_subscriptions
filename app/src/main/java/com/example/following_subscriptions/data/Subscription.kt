package com.example.following_subscriptions.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "subcription")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val category: String,
    val date: String,
    val price: String,
    val iconRes: Int,
    val colorInt: Int
) {
}