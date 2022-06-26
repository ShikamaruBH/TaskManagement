package com.shikamarubh.taskmanagement.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shikamarubh.taskmanagement.data.TaskRepository
import com.shikamarubh.taskmanagement.model.Status
import com.shikamarubh.taskmanagement.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    // Đối tượng firestore dùng để giao tiếp với Firestore
    private val db = Firebase.firestore
    val auth = Firebase.auth
    // Đối tượng collection reference kết nối đến collection tương ứng trên Firestore
    val collRef = db.collection("tasks")

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    private val _todoTaskList = MutableStateFlow<List<Task>>(emptyList())
    private val _doingTaskList = MutableStateFlow<List<Task>>(emptyList())
    private val _doneTaskList = MutableStateFlow<List<Task>>(emptyList())

    val taskList = _taskList.asStateFlow()
    val todoTaskList = _todoTaskList.asStateFlow()
    val doingTaskList = _doingTaskList.asStateFlow()
    val doneTaskList = _doneTaskList.asStateFlow()

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

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTodoTasks().distinctUntilChanged()
                .collect {
                        listOfTask ->
                    if (listOfTask.isNullOrEmpty()){
                        Log.d("DEBUG Task","Null or empty Todo Task List")
                        _todoTaskList.value= emptyList()
                    }else{
                        _todoTaskList.value=listOfTask
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllDoingTasks().distinctUntilChanged()
                .collect {
                        listOfTask ->
                    if (listOfTask.isNullOrEmpty()){
                        Log.d("DEBUG Task","Null or empty Doing Task List")
                        _doingTaskList.value= emptyList()
                    }else{
                        _doingTaskList.value=listOfTask
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllDoneTasks().distinctUntilChanged()
                .collect {
                        listOfTask ->
                    if (listOfTask.isNullOrEmpty()){
                        Log.d("DEBUG Task","Null or empty Done Task List")
                        _doneTaskList.value= emptyList()
                    }else{
                        _doneTaskList.value=listOfTask
                    }
                }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch {
        collRef.document(task.id).set(task)
        taskRepository.addTask(task)
    }
    fun updateTask(task: Task) = viewModelScope.launch {
        collRef.document(task.id).update("status", task.status)
        taskRepository.updateTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch {
        collRef.document(task.id).delete()
        taskRepository.deleteTask(task)
    }
    fun deleteAllTask() = viewModelScope.launch { taskRepository.deleteAllTasks() }
    fun toToDo(task: Task) = viewModelScope.launch { taskRepository.toToDo(task)
        collRef.document(task.id).update("status", Status.TODO)
    }
    fun toDoing(task: Task) = viewModelScope.launch { taskRepository.toDoing(task)
        collRef.document(task.id).update("status", Status.DOING)
    }
    fun toDone(task: Task) = viewModelScope.launch { taskRepository.toDone(task)
        collRef.document(task.id).update("status", Status.DONE)
    }
    fun makeTaskImportance(task: Task) = viewModelScope.launch {
        collRef.document(task.id).update("isImportant", true)
        taskRepository.makeTaskImportance(task) }
    fun makeTaskNormal(task: Task) = viewModelScope.launch {
        collRef.document(task.id).update("isImportant", false)
        taskRepository.makeTaskNormal(task) }
    fun getTaskById(id: String): LiveData<Task>{
        val task = MutableLiveData<Task>()
        viewModelScope.launch {
            task.value = taskList.value.filter { p -> p.id == id }[0]
        }
        return task
    }
    fun getTasksByProjectId(id: String): MutableStateFlow<List<Task>>{
        val tasks = MutableStateFlow<List<Task>>(emptyList())
        viewModelScope.launch {
            tasks.value = taskList.value.filter { p -> p.projectId == id }
        }
        return tasks
    }

    fun getToDoTasksByProjectId(id: String): MutableStateFlow<List<Task>>{
        val tasks = MutableStateFlow<List<Task>>(emptyList())
        viewModelScope.launch {
            tasks.value = todoTaskList.value.filter { p -> p.projectId == id  }
        }
        return tasks
    }
    fun getDoingTasksByProjectId(id: String): MutableStateFlow<List<Task>>{
        val tasks = MutableStateFlow<List<Task>>(emptyList())
        viewModelScope.launch {
            tasks.value = doingTaskList.value.filter { p -> p.projectId == id  }
        }
        return tasks
    }
    fun getDoneTasksByProjectId(id: String): MutableStateFlow<List<Task>>{
        val tasks = MutableStateFlow<List<Task>>(emptyList())
        viewModelScope.launch {
            tasks.value = doneTaskList.value.filter { p -> p.projectId == id  }
        }
        return tasks
    }
}