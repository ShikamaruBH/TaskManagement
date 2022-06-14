package com.shikamarubh.taskmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Project(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var description: String,
    var isArchived: Boolean,
    var isDeleted: Boolean,
)