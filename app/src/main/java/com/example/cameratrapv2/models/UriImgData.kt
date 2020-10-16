package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uri_data")
data class UriImgData(
    @PrimaryKey
    val number: String,
    val uri_img: String,
    val date_stamp: Long
) {
}