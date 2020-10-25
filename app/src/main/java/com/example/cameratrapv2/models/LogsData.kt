package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "logs_data")
data class LogsData(
    val number: String,
    val log: String,
    @PrimaryKey
    val date_stamp: Long
) {
    override fun toString(): String {
        val df = SimpleDateFormat("dd.MMM.yyyy  HH:mm", Locale.ROOT)
        val date = df.format(Date(date_stamp))
        return "$date : [ $log ]"
    }
}