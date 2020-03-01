package com.grobocop.speech2text.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionsManager {

    fun checkPermissions(activity: Activity): Boolean = ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.INTERNET
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    ) == PackageManager.PERMISSION_GRANTED

    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
            ),
            PackageManager.PERMISSION_GRANTED
        )
    }
}