package com.shikamarubh.taskmanagement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.floor


//Project
@Composable
fun ProjectScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        CardProject()
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var textName by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 10.dp),
                    value = textName,
                    onValueChange = { textName = it },
                    label = {
                        Text(
                            "Name Project",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                )

                var textDescription by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 10.dp),
                    value = textDescription,
                    onValueChange = { textDescription = it },
                    label = {
                        Text(
                            "Description Project",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                )
                Button(onClick = {
                    status.value = !status.value
                }, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
                    Text(text = " ADD PROJECT",color=Color.Black)
                }
                if (status.value)
                    CardProject(data = listOf("Python","Data Science","Java spring"))
            }
        }
    }
}

//To do screen
@Composable
fun ToDoScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var textTask by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 20.dp),
                    value = textTask,
                    onValueChange = { textTask = it },
                    label = { Text("Name Task", fontSize = 14.sp, fontWeight = FontWeight.Bold,color=Color.Black) }
                )

                Button(onClick = {
                    status.value = !status.value
                }, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
                    Text(text = " ADD TASK",color=Color.Black)
                }
                CardTask(data = listOf("Crawl dữ liệu","Tạo đặc trưng mới","Đánh giá mô hình"))
            }
        }
    }
}

//DoneScreen
@Composable
fun DoneScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTask(data = listOf("Tạo file js","Reset css","Gắn link html"))
            }
        }
    }
}

//DoingScreen
@Composable
fun DoingScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTask(data = listOf("Đi ngủ","Ăn cơm","Tắm rửa"))
            }
        }
    }
}

//Archive
@Composable
fun ArchiveScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        CardProject()
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTask(data = listOf("Python","Data Science","Java spring"))
            }
        }
    }
}

//TrashScreen
@Composable
fun TrashScreen() {
    val status = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        CardProject()
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTask(data = listOf("Python","Data Science","Java spring"))
                Button(onClick = {
                    status.value = !status.value
                }, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(245, 62, 62))) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
                    Text(text = " DELETE ALL",color=Color.White)
                }
                if (status.value)
                    Row(modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 50.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically){
                        ButtonConfirmDeleteAllProject()
                        ButtonDeclineDeleteAllProject()
                    }
            }
        }
    }
}

@Composable
fun CardProject(data: List<String>){
    LazyColumn{
        items(data){
                item ->
            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14),
                elevation = 4.dp
            ) {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                    ) {
                        Text(text = item, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.Black)
                        Text(text = "Chạy Deadline nè !", fontSize = 14.sp, fontWeight = FontWeight.Light,color=Color.Black)
                    }
            }
        }
    }
}


//Card task
@Composable
fun CardTask(data: List<String>) {
    LazyColumn{
        items(data){
                item ->
            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(text = item, fontSize = 16.sp, fontWeight = FontWeight.Bold,color=Color.Black)
                }
            }
        }
    }
}