package com.shikamarubh.taskmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val isArchived: Boolean,
    val isDeleted: Boolean,
    val projectId: UUID,
)
