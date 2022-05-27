package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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


//Màn hình chính
@Preview
@Composable
fun MainScreen(){
    Scaffold(
        topBar = { TopAppBar() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    CardProject()
                    CardProject()
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    ButtonAddProject()
                }
            }
        },
        backgroundColor = Color(251, 249, 245),
    )
}


//Màn hình Task
@Preview
@Composable
fun TaskScreen(){
    Scaffold(
        topBar = { TopAppBar() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        ButtonToDoChecked()
                        ButtonDone()
                        ButtonDoing()
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        CardTask()
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    ButtonAddTask()
                }
            }
        },
        backgroundColor = Color(251, 249, 245),
    )
}

//Màn hình Archive
@Preview
@Composable
fun ArchiveScreen(){
    Scaffold(
        topBar = { TopAppBar() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    CardProject()
                }

            }
        },
        backgroundColor = Color(251, 249, 245),
    )
}

//Màn hình thùng rác
@Preview
@Composable
fun TrashScreen(){
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { TopAppBar() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    CardProject()
                    CardProject()
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    ButtonDeleteAllProject()
                }
            }
        },
        backgroundColor = Color(251, 249, 245),
    )
}

//Card task
@Composable
fun CardTask() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        modifier = paddingModifier,
    ) {
        Column(modifier = paddingModifier) {
            Text(text = "Crawl dữ liệu", fontSize = 10.sp, fontWeight = FontWeight.Bold,color=Color.Black)
        }
    }
}

//Card dự án
@Composable
fun CardProject() {
    val paddingModifier = Modifier.padding(17.dp)
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

//Thanh top menu
@Composable
fun TopAppBar() {
    TopAppBar(
        title = { Text(text = "Task Manager App", fontWeight = FontWeight.Bold, color = Color(125, 118, 102)) },
        backgroundColor = Color(255, 255, 255),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/
            }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Localized description",
                )
            }
        }
    )
}

//Button To-do
@Composable
fun ButtonToDo() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(125, 117, 109)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "TO-DO ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Button To-do Checked
@Composable
fun ButtonToDoChecked() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        modifier = paddingModifier.border(3.dp, Color(146, 180, 236),shape= RoundedCornerShape(50)),
        backgroundColor = Color(125, 117, 109)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "TO-DO ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Button Doing
@Composable
fun ButtonDoing() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(245, 160, 62)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DOING ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Button Doing Checked
@Composable
fun ButtonDoingChecked() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        modifier = paddingModifier.border(3.dp, Color(146, 180, 236),shape= RoundedCornerShape(50)),
        backgroundColor = Color(245, 160, 62)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DOING ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Button Done
@Composable
fun ButtonDone() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(139, 192, 106)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DONE ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Button Done Checked
@Composable
fun ButtonDoneChecked() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        modifier = paddingModifier.border(3.dp, Color(146, 180, 236),shape= RoundedCornerShape(50)),
        backgroundColor = Color(139, 192, 106)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DONE ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
            Text(text = " 24", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
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

//Nút thêm task
@Composable
fun ButtonAddTask() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
        Text(text = " ADD TASK",color=Color.White)
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