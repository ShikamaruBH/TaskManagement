package com.shikamarubh.taskmanagement.data

import androidx.room.*
import com.shikamarubh.taskmanagement.model.Project
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ProjectDao {
    @Query("select * from project where userid =:userId")
    fun getUserProjects(userId: String) : Flow<List<Project>>

    @Query("select * from project where isarchived = 0 and isdeleted = 0 and userid =:userId")
    fun getAllProjects(userId: String) : Flow<List<Project>>

    @Query("select * from project where isarchived = 1 and isdeleted = 0 and userid =:userId")
    fun getAllArchivedProjects(userId: String) : Flow<List<Project>>

    @Query("select * from project where isdeleted = 1 and userid =:userId")
    fun getAllDeletedProjects(userId: String) : Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProject(project: Project)

    @Query("delete from project where isdeleted = 1 and userid =:userId")
    suspend fun deleteAllProject(userId: String)

    @Query("delete from project")
    suspend fun deleteAllProjects()

    @Delete
    suspend fun deleteProject(project: Project)
}