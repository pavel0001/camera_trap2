package com.example.cameratrapv2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.CommandData
import com.example.cameratrapv2.models.LogsData
import com.example.cameratrapv2.models.UriImgData

@Dao
interface TotalDao {
    //-------------URI
    @Update(entity = UriImgData::class)
    fun updateUriImgData(data: UriImgData)

    @Query("DELETE FROM uri_data WHERE date_stamp < :date")
    fun deleteOldUriImg(date: Long)

    @Query("SELECT * FROM uri_data")
    fun getUriImg(): LiveData<List<UriImgData>>

    @Insert(entity = UriImgData::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertUriImgData(data: UriImgData)

    //--------------LOGS
    @Query("SELECT * FROM logs_data")
    fun getLogsData(): LiveData<List<LogsData>>

    @Insert(entity = LogsData::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertLogsData(data: LogsData)

    @Update(entity = LogsData::class)
    fun updateLogsData(data: LogsData)

    @Query("DELETE FROM logs_data WHERE number = :num")
    fun deleteLogsDataFromNumber(num: String)

    //--------------------------Camera
    @Query("SELECT * from camera_data")
    fun getCameraDataAll(): LiveData<List<CameraData>>

    @Query("SELECT * from camera_data")
    fun getCameraData(): List<CameraData>

    @Insert(entity = CameraData::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCameraData(camera: CameraData)

    @Update(entity = CameraData::class)
    suspend fun updateCameraData(camera: CameraData)

    @Delete(entity = CameraData::class)
    suspend fun delete(camera: CameraData)

    //-----------------------CMD
    @Query("SELECT * from cmd_data")
    fun getCmd(): LiveData<List<CommandData>>

    @Update(entity = CommandData::class)
    suspend fun updateCmdData(cmd: CommandData)

}