package com.shikamarubh.taskmanagement.data

import com.shikamarubh.taskmanagement.model.Status
import com.shikamarubh.taskmanagement.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks().flowOn(Dispatchers.IO)
    fun getAllTodoTasks(): Flow<List<Task>> = taskDao.getAllTodoTasks().flowOn(Dispatchers.IO)
    fun getAllDoingTasks(): Flow<List<Task>> = taskDao.getAllDoingTasks().flowOn(Dispatchers.IO)
    fun getAllDoneTasks(): Flow<List<Task>> = taskDao.getAllDoneTasks().flowOn(Dispatchers.IO)
    suspend fun addTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
    suspend fun toToDo(task: Task) {
        task.status = Status.TODO;
        taskDao.updateTask(task)
    }
    suspend fun toDoing(task: Task) {
        task.status = Status.DOING;
        taskDao.updateTask(task)
    }
    suspend fun toDone(task: Task) {
        task.status = Status.DONE;
        taskDao.updateTask(task)
    }
    suspend fun makeTaskImportance(task: Task){
        task.isImportant = true;
        taskDao.updateTask(task)
    }
    suspend fun makeTaskNormal(task: Task){
        task.isImportant = false;
        taskDao.updateTask(task)
    }

}