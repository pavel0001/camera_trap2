package com.example.cameratrapv2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cmd_data")
data class CommandData(
    @PrimaryKey
    val key: Int,
    val name: String,
    val value: String
    ) {
    override fun toString(): String {
        return "CommandData(name='$name', value='$value', key=$key)"
    }

}