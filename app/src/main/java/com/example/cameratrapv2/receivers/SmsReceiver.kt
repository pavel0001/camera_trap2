package com.example.cameratrapv2.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import android.util.Log
import com.example.cameratrapv2.utils.APP_ACTIVITY



class SmsReceiver: BroadcastReceiver() {
    val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("MyTag", "onReceived")
        if(intent != null && intent.action.equals(SMS_RECEIVED)) {
            var sms_from = ""
            var sms_body = ""
            for (message in getMessagesFromIntent(intent)) {
                if (message == null) {
                    Log.i("MyTag", "message is null")
                    break
                }
                sms_from = message.displayOriginatingAddress
                sms_body = message.displayMessageBody
            }
            Log.i("MyTag", "$sms_from   $sms_body")
            APP_ACTIVITY.separateMessage(sms_body, sms_from)
        }
    }
}