package com.grobocop.speech2text.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SpeechRecognitionService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }
}