package com.shikamarubh.taskmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project(
    @PrimaryKey
    var id: String = "",
    var title: String? = null,
    var description: String? = null,
    @field:JvmField
    var isArchived: Boolean? = null,
    @field:JvmField
    var isDeleted: Boolean? = null,
    var userId: String? = null
)