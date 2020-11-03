package com.example.cameratrapv2.database

import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.CommandData
import com.example.cameratrapv2.models.LogsData
import com.example.cameratrapv2.models.UriImgData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.jar.Manifest

class TotalRepository(private val dao: TotalDao) {
    //######################Camera##############################
    fun getCameraDataAll(): LiveData<List<CameraData>> = dao.getCameraDataAll()

    suspend fun insertCameraData(camera: CameraData){
        dao.insertCameraData(camera)
    }
    suspend fun updateCameraData(camera: CameraData){
        dao.updateCameraData(camera)
    }
    suspend fun updateCameraDataFromList(camera: List<CameraData>){
        dao.updateCameraDataFromList(camera)
    }
    suspend fun deleteCamera(camera: CameraData){
        dao.delete(camera)
    }
    fun getCameraDataInList(): List<CameraData> = dao.getCameraDataInList()
    fun getCameraDataFromNumber(number: String) = dao.getCameraDataFromNumber(number)

    //######################URI##############################

    suspend fun updateUriImgData(data: UriImgData){
        dao.updateUriImgData(data)
    }
    suspend fun deleteOldUriImg(date: Long){
        dao.deleteOldUriImg(date)
    }
    fun getUriImgLiveData(): LiveData<List<UriImgData>> = dao.getUriImgLiveData()

    fun getUriImgListFromNumber(number: String): List<UriImgData> = dao.getUriImgListFromNumber(number)

    fun getIdListFromUriData(): List<String> = dao.getIdListFromUriData()

    suspend fun insertUriImgData(data: UriImgData){
        dao.insertUriImgData(data)
    }
    suspend fun insertUriImgDataFromList(data: List<UriImgData>) = dao.insertUriImgDataFromList(data)

    fun getLastUriDataFromNumber(number: String): UriImgData = dao.getLastUriDataFromNumber(number)

    fun getHowMuchImagesHasCamera(number: String): Int = dao.getHowMuchImagesHasCamera(number)

    //######################LOGS##############################

    fun getLogsData(): LiveData<List<LogsData>> = dao.getLogsData()
    suspend fun insertLogsData(data: LogsData){
        dao.insertLogsData(data)
    }
    suspend fun updateLogsData(data: LogsData){
        dao.updateLogsData(data)
    }
    suspend fun deleteLogsDataFromNumber(num: String){
        dao.deleteLogsDataFromNumber(num)
    }
    //######################CMD##############################

    fun getCmdLiveData(): LiveData<List<CommandData>> = dao.getCmd()
    suspend fun updateCmdData(cmd: CommandData) = dao.updateCmdData(cmd)
    fun getCmdList(): List<CommandData> = dao.getCmdList()

     fun initializedCmd(viewModelScope: CoroutineScope){
        val cmdList = listOf<CommandData>(
            CommandData(0, "default", "default","user"),
            CommandData(1, "PIR", "*202#0#","*202#3#"),
            CommandData(2, "GET", "*500#"),
            CommandData(3, "ADD", "*100#number#"),
            CommandData(4, "DELETE", "*101#number#"),
            CommandData(5, "ANY", ""),
            CommandData(6, "SET", "*205#time#"),
            CommandData(7, "SMS", "*140#0#","*140#2#"),
            CommandData(8, "ANY", "*160#")
            )
         viewModelScope.launch(Dispatchers.IO) {  insertCMD(cmdList)}

    }
    suspend fun insertCMD(cmd: List<CommandData>) = dao.insertCmd(cmd)
    //Sen Command
    fun sendCmd(number: String, text: String){
        Log.i("MyTag", "sendCmd to $number with text $text")
            SmsManager.getDefault().sendTextMessage(number, null, text, null, null)
    }
}