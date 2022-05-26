package com.shikamarubh.taskmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Project(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val content: String,
    val status: Status,
    val is_important: Boolean,
)
