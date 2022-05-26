package com.shikamarubh.taskmanagement.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikamarubh.taskmanagement.data.TaskRepository
import com.shikamarubh.taskmanagement.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList = _taskList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTasks().distinctUntilChanged()
                .collect {
                    listOfTask ->
                    if (listOfTask.isNullOrEmpty()){
                        Log.d("DEBUG Task","Null or empty Task List")
                        _taskList.value= emptyList()
                    }else{
                        _taskList.value=listOfTask
                    }
                }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch { taskRepository.addTask(task) }
    fun updateTask(task: Task) = viewModelScope.launch { taskRepository.updateTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { taskRepository.deleteTask(task) }
    fun deleteAllTask() = viewModelScope.launch { taskRepository.deleteAllTasks() }

}