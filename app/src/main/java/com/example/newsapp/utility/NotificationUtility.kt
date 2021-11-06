package com.example.newsapp.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.newsItemListFragment


class NotificationUtility(var context: Context) {
    companion object {
        public var CHANNEL_ID = "123"
        public var CHANNEL_NAME = "News notifcations"
    }
    private var notificationManager: NotificationManager

    init {
        notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    public fun sendNotification( id:Int, title: String,  content: String, url:String){
       var intent = Intent(context, newsItemListFragment::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title",title)
        var pIntent = PendingIntent.getActivity(context, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_baseline_campaign_24)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setShowWhen(true)
            .setAutoCancel(true)
            .setColor(Color.RED)
            .setContentIntent(pIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Open", pIntent)
            .build()
        notificationManager.notify(id, notification)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "This is a sample channel"
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
    }
}