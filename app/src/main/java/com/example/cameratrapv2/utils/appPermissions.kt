package com.example.cameratrapv2.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


const val SEND_SMS = Manifest.permission.SEND_SMS
const val READ_SMS = Manifest.permission.READ_SMS
const val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE



const val PERMISSION_REQUEST = 200

fun checkPermissions(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= 23
        && ContextCompat.checkSelfPermission(APP_ACTIVITY, permission) != PackageManager.PERMISSION_GRANTED ){
        ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), PERMISSION_REQUEST)
        false
    }else true
}