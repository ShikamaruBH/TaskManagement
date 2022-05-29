package com.shikamarubh.taskmanagement.viewmodel

import androidx.room.TypeConverter
import com.shikamarubh.taskmanagement.model.Status
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStatus(value: String) = enumValueOf<Status>(value)

    @TypeConverter
    fun toStatus(value: Status) = value.name

}
