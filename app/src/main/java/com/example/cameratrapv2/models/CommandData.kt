package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cmd_data")
data class CommandData(
    @PrimaryKey
    val key: Int,
    val name: String,
    val valueOn: String,
    val valueOff: String = "null"
    ) {
    override fun toString(): String {
        return "CommandData(key=$key, name='$name', valueOn='$valueOn', valueOff='$valueOff')"
    }

}