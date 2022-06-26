package com.shikamarubh.taskmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
    val id: String = "",
    var content: String? = null,
    var status: Status? = null,
    @field:JvmField
    var isImportant: Boolean? = null,
    val projectId: String? = null,
)
