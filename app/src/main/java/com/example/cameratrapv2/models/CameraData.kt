package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camera_data")
data class CameraData(
    @PrimaryKey
    val number: String,
    var signal: String = "0",
    var storage: String = "0",
    var battery: String = "0",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
var last_update: Long = 0L,
var last_photo: Long = 0L,
var last_photo_uri: String ="",
var total_images: Int = 0,
var pir_state: Boolean = true,
var sms_state: Boolean = true) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CameraData

        if (number != other.number) return false
/*        if (signal != other.signal) return false
        if (storage != other.storage) return false
        if (battery != other.battery) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (last_update != other.last_update) return false
        if (last_photo != other.last_photo) return false
        if (total_images != other.total_images) return false*/

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + signal.hashCode()
        result = 31 * result + storage.hashCode()
        result = 31 * result + battery.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + last_update.hashCode()
        result = 31 * result + last_photo.hashCode()
        result = 31 * result + total_images
        return result
    }

    override fun toString(): String {
        return "CameraData(number='$number', signal='$signal', storage='$storage', battery='$battery', " +
                "latitude=$latitude, longitude=$longitude, last_update=$last_update, last_photo=$last_photo," +
                " total_images=$total_images, pir_state=$pir_state, sms_state=$sms_state)"
    }

}
