package com.shikamarubh.taskmanagement.di

import android.content.Context
import androidx.room.Room
import com.shikamarubh.taskmanagement.data.ProjectDao
import com.shikamarubh.taskmanagement.data.TaskDao
import com.shikamarubh.taskmanagement.data.TaskManagementDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideTasksDao(taskManagementDatabase: TaskManagementDatabase):TaskDao=taskManagementDatabase.taskDao()

    @Singleton
    @Provides
    fun provideProjectDao(taskManagementDatabase: TaskManagementDatabase) : ProjectDao
        = taskManagementDatabase.projectDao()

    @Singleton
    @Provides
    fun provideTaskManagementDatabase(@ApplicationContext context: Context): TaskManagementDatabase
            = Room.databaseBuilder(
        context,
        TaskManagementDatabase::class.java,
        "TaskManagement"
    )
        .fallbackToDestructiveMigration()
        .build()
}