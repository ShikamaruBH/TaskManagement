package com.shikamarubh.taskmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    var content: String,

    val status: Status,

    val isImportant: Boolean,

    val projectId: UUID,
)
