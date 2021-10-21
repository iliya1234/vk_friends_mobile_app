package com.example.vkfriends.helpers

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.vkfriends.R
import com.example.vkfriends.activities.LoginActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

 class MyApplication:Application() {

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
        }
    }

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        VK.addTokenExpiredHandler(tokenTracker)
    }
}