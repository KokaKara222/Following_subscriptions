package com.example.following_subscriptions

import androidx.work.Worker
import androidx.work.WorkerParameters
import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationWorker (
    context: Context,
    workerParams: WorkerParameters,
): Worker(context, workerParams){
    override fun doWork(): Result {
        val subName = inputData.getString("SUB_NAME")?: "Подписка"
        val subPrice = inputData.getString("SUB_PRICE") ?: "0"

        sendNotification(subName, subPrice)
        return Result.success()
    }

    private fun sendNotification(name: String, price: String){
        val channelId = "subscription_alerts"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Напоминание о подписках",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Уведомление об истечении срока оплаты подписок"
            }
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_subs)
            .setContentTitle("Скоро списание подписки!")
            .setContentText("Завтра спишется $price p. за подписку $name")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(name.hashCode(), notification)

    }
}