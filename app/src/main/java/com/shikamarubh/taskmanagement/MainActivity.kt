package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    Greeting("Android")
                }
            }
        }
    }
}

@Preview
@Composable
//Màn hình chính
fun GetScaffold(){
    Scaffold(
        topBar = { TopAppBar() },
        content = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                CardProject()
                CardProject()
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally){
                    ButtonAddProject()
                }
        },
        backgroundColor = Color(251, 249, 245),
    )
}


//Thanh top menu
@Composable
fun TopAppBar() {
    val expanded = remember { mutableStateOf(false)}

    TopAppBar(
        title = { Text(text = "Task Manager App", fontWeight = FontWeight.Bold, color = Color(125, 118, 102)) },
        backgroundColor = Color(255, 255, 255),
        navigationIcon = {
            IconButton(onClick = {
                // do something here
            }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

//Card dự án
@Composable
fun CardProject() {
    val paddingModifier = Modifier.padding(19.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(14),
        modifier = paddingModifier,
    ) {
        Column(modifier = paddingModifier) {
            Text(text = "Khoa học dữ liệu", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold,color=Color.Black)
            Text(text = "Chạy Deadline nè !", fontSize = 12.sp, fontWeight = FontWeight.Light,color=Color.Black)
        }
    }
}

//Nút xoá dự án trong thùng rác
@Composable
fun ButtonDeleteAllProject() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(245, 62, 62))) {
        Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
        Text(text = " DELETE ALL",color=Color.White)
    }
}

//Nút thêm task
@Composable
fun ButtonAddTask() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
        Text(text = " ADD Task",color=Color.White)
    }
}


//Nút thêm dự án
@Composable
fun ButtonAddProject() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
        Text(text = " ADD PROJECT",color=Color.White)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    TaskManagementTheme {
        Greeting("Android")
    }
}