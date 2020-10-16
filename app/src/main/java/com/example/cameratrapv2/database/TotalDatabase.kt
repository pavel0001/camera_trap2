package com.example.cameratrapv2.database



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.CommandData
import com.example.cameratrapv2.models.LogsData
import com.example.cameratrapv2.models.UriImgData

@Database(entities = arrayOf(CameraData::class, LogsData::class, UriImgData::class, CommandData::class), version = 1)
public abstract class TotalDatabase: RoomDatabase() {

    abstract fun dao(): TotalDao

    companion object{
        @Volatile
        private var INSTANCE: TotalDatabase? = null

        fun getDatabase(context: Context): TotalDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TotalDatabase::class.java,
                    "room_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}