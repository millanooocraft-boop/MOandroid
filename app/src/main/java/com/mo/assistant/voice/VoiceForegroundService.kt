package com.mo.assistant.voice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.mo.assistant.MainActivity
import com.mo.assistant.R

/**
 * VoiceForegroundService — placeholder for Phase 2 (Wake Word).
 *
 * TODO: Integrate Porcupine SDK for wake word detection:
 *  - AccessKey from Picovoice console
 *  - "ya-mo.ppn" model file in assets/
 *  - Audio recording + VAD
 *  - Whisper.cpp for STT
 */
class VoiceForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "mo_voice_channel"
        private const val NOTIFICATION_ID = 1001
        const val ACTION_START = "com.mo.assistant.START_VOICE"
        const val ACTION_STOP = "com.mo.assistant.STOP_VOICE"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP -> {
                stopSelf()
                return START_NOT_STICKY
            }
            else -> startForeground(NOTIFICATION_ID, buildNotification())
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "MO Voice Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Listening for \"يا مو\" wake word"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = PendingIntent.getService(
            this, 1,
            Intent(this, VoiceForegroundService::class.java).apply { action = ACTION_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MO يستمع 👂")
            .setContentText("قول \"يا مو\" لتنشيط")
            .setSmallIcon(R.drawable.ic_mic)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_mic, "إيقاف", stopIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}