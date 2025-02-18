package com.example.lab1advand.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.provider.Settings

class AirplaneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0

            val message = if (isAirplaneModeOn) "Airplane Mode is ON" else "Airplane Mode is OFF"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
