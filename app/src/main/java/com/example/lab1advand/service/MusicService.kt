package com.example.lab1advand.service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.lab1advand.MainActivity
import com.example.lab1advand.R

class MusicService : Service() {

    companion object {
        const val ACTION_PLAY = "PLAY"
        const val ACTION_PAUSE = "PAUSE"
        const val ACTION_STOP = "STOP"
        private const val CHANNEL_ID = "MusicService"
        private const val NOTIFY_ID = 101
    }

    private var player: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer().apply {
            val descriptor = assets.openFd("music.mp3")
            setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            prepare()
            isLooping = false
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> if (!isPlaying) {
                player?.start()
                isPlaying = true
                startForegroundService()
            }
            ACTION_PAUSE -> if (isPlaying) {
                player?.pause()
                isPlaying = false
                updateNotification()
            }
            ACTION_STOP -> stopService()
        }
        return START_NOT_STICKY
    }

    private fun startForegroundService() {
        createChannel()
        startForeground(NOTIFY_ID, buildNotification())
    }

    private fun updateNotification() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFY_ID, buildNotification())
    }

    private fun buildNotification(): Notification {
        val mainIntent = Intent(this, MainActivity::class.java)
        val mainPending = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val playIntent = Intent(this, MusicService::class.java).apply { action = ACTION_PLAY }
        val playPending = PendingIntent.getService(this, 1, playIntent, PendingIntent.FLAG_IMMUTABLE)

        val pauseIntent = Intent(this, MusicService::class.java).apply { action = ACTION_PAUSE }
        val pausePending = PendingIntent.getService(this, 2, pauseIntent, PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, MusicService::class.java).apply { action = ACTION_STOP }
        val stopPending = PendingIntent.getService(this, 3, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Service")
            .setContentText(if (isPlaying) "Playing Daft PUNKKKKK" else "Grusni")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(mainPending)
            .addAction(NotificationCompat.Action(0, if (isPlaying) "Pause" else "Play", if (isPlaying) pausePending else playPending))
            .addAction(NotificationCompat.Action(0, "Stop", stopPending))
            .setOngoing(isPlaying)
            .build()
    }
    private fun stopService() {
        player?.stop()
        player?.release()
        player = null
        isPlaying = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        isPlaying = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "Music", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }
}
















































































































//dont even try to copy:))


