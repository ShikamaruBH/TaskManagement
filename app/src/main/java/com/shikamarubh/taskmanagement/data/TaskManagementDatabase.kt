package com.shikamarubh.taskmanagement.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shikamarubh.taskmanagement.model.Project
import com.shikamarubh.taskmanagement.model.Task
import com.shikamarubh.taskmanagement.viewmodel.Converters

@Database(entities = [Project::class,Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskManagementDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
}