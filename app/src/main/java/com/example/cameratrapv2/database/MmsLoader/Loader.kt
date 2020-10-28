package com.example.cameratrapv2.database.MmsLoader

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony.BaseMmsColumns.*
import android.provider.Telephony.MmsSms.TYPE_DISCRIMINATOR_COLUMN
import android.util.Log
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.models.UriImgData
import com.example.cameratrapv2.utils.APP_ACTIVITY
import java.lang.Exception
import java.util.*

class Loader(context: Context?, val numberList: List<String>) : ContentResolver(context) {

    val resolver = context?.contentResolver



    fun getAllMmsData(idList: List<String>): List<UriImgData> {
        val resultListWithUriData = mutableListOf<UriImgData>()
        val projection = arrayOf(
            _ID,
            TYPE_DISCRIMINATOR_COLUMN,
            MESSAGE_BOX,
            DATE
        )
        val uri = Uri.parse("content://mms-sms/complete-conversations/")
        val cursor = resolver?.query(uri, projection, null, null, null)
        if(cursor != null && cursor.moveToFirst()){
            do{

                    val date = Date(cursor.getLong(cursor.getColumnIndex(DATE)) * 1000)
                    if(date.before(Date(Date().time - 2592000000))){
                        continue
                    }
                    val descrim = cursor.getString(cursor.getColumnIndex(TYPE_DISCRIMINATOR_COLUMN))
                    val box = cursor.getString(cursor.getColumnIndex(MESSAGE_BOX))
                    val id = cursor.getString(cursor.getColumnIndex(_ID))


                    if((!idList.contains(id)) && descrim.equals("mms") && box.equals(MESSAGE_BOX_INBOX.toString())){
                        val uriMmsLevel = Uri.parse("content://mms/$id/addr")
                        val selectionMmsLevel = "msg_id = $id"
                        val cursorMmsLevel = resolver?.query(
                            uriMmsLevel,
                            null,
                            selectionMmsLevel,
                            null,
                            null
                        )

                        if(cursorMmsLevel != null && cursorMmsLevel.moveToFirst()) {
                            do {
                                val address = cursorMmsLevel.getString(
                                    cursorMmsLevel.getColumnIndex("address")
                                ).replaceFirst("+", "")
                                val type = cursorMmsLevel.getString(cursorMmsLevel.getColumnIndex("type")).toInt()
                                if(type == 137 && numberList.contains(address)){

                                    val number = address

                                        val imageUri = getMmsUriFromId(id)
                                    val uriData = UriImgData(
                                        number = number,
                                        uri_img = imageUri.toString(),
                                        date_stamp = date.time,
                                        mms_id = id
                                    )
                                    resultListWithUriData.add(uriData)
                                }
                            } while (cursorMmsLevel.moveToNext())
                        }
                        cursorMmsLevel?.close()
                    }

            }while(cursor.moveToNext())
        }
        cursor?.close()
        return resultListWithUriData
    }
    private fun getMmsUriFromId(id: String): Uri?{
        val selectionPart = "mid=$id"
        val uri = Uri.parse("content://mms/part")
        lateinit var cPart: Cursor
            try {
                cPart = resolver?.query(uri, null, selectionPart, null, null)!!
            }catch (e: Exception){
                e.printStackTrace()
            }
        if(cPart.moveToFirst()){
            do{
                val partId = cPart.getString(cPart.getColumnIndex("_id"))
                val type = cPart.getString(cPart.getColumnIndex("ct"))
                if("image/jpeg" == type || "image/bmp" == type ||
                    "image/gif" == type || "image/jpg" == type ||
                    "image/png" == type){
                    return Uri.parse("content://mms/part/$partId")
                }
            }while(cPart.moveToNext())
        }
        return null
    }

}