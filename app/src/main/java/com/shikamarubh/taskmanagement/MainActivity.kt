package com.shikamarubh.taskmanagement

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shikamarubh.taskmanagement.model.Status
import com.shikamarubh.taskmanagement.model.Task
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import com.shikamarubh.taskmanagement.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.d("cc","sasdasd")
                    val taskViewModel = viewModel<TaskViewModel>()
                    //add task
//                    taskViewModel.addTask(Task(content = "aaaa", status = Status.TODO, isImportant = true, projectId = UUID.randomUUID()))
//                    taskViewModel.addTask(Task(content = "nnnn", status = Status.DONE, isImportant = true, projectId = UUID.randomUUID()))
//                    taskViewModel.addTask(Task(content = "bbbb", status = Status.DOING, isImportant = true, projectId = UUID.randomUUID()))
                    //delete all task
//                    taskViewModel.deleteAllTask()

                    //update task and delete--ko lay duoc ra doi tuong task = ham getByid, need help
//                    val data by taskViewModel.taskList.collectAsState()
//                    var i = 1
//                    for (task in data){
//                        Log.d("i",i.toString())
//                        if (i==1){
//                            task.content = "sugoi"
//                            taskViewModel.updateTask(task)
//                            Log.d("Debug task",task.content)
//                        }
//                        else if (i==2){
//                            Log.d("Debug2 task",task.content)
//                            taskViewModel.deleteTask(task)
//                        }
//                        i++
//                    }




                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagementTheme {
        Greeting("Android")
    }
}