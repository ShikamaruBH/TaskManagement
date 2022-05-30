package com.shikamarubh.taskmanagement.data

import androidx.room.*
import com.shikamarubh.taskmanagement.model.Project
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ProjectDao {
    @Query("select * from project where isarchived = false and isdeleted = false")
    fun getAllProjects() : Flow<List<Project>>

    @Query("select * from project where isarchived = true and isdeleted = false")
    fun getAllArchivedProjects() : Flow<List<Project>>

    @Query("select * from project where isdeleted = true")
    fun getAllDeletedProjects() : Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProject(project: Project)

    @Query("delete from project")
    suspend fun deleteAllProjects()

    @Delete
    suspend fun deleteProject(project: Project)
}