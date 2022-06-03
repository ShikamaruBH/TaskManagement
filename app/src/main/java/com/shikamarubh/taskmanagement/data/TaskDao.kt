package com.shikamarubh.taskmanagement.data

import androidx.room.*
import com.shikamarubh.taskmanagement.model.Status
import com.shikamarubh.taskmanagement.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task where status = 'TODO'")
    fun getAllTodoTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task where status = 'DOING'")
    fun getAllDoingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task where status = 'DONE'")
    fun getAllDoneTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id=:id")
    suspend fun getTaskById(id: UUID): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Delete
    suspend fun deleteTask(task: Task)
}