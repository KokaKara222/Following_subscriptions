package com.example.following_subscriptions.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val category: String,
    val date: String,
    val price: String,
    val period: String,
    val iconRes: Int,
    val imageUri: String?,
    val colorInt: Int
) {
}