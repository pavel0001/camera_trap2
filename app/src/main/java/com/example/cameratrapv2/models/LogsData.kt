package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs_data")
data class LogsData(
    @PrimaryKey
    val number: String,
    val log: String,
    val date_stamp: Long
) {
}