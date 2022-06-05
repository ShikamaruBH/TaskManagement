package com.shikamarubh.taskmanagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentTransaction


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
                },  colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(14.dp))  {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
                    Text(text = " ADD PROJECT", fontSize = 18.sp,fontWeight = FontWeight.ExtraBold,color=Color.White)
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
                },  colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(14.dp))  {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
                    Text(text = " ADD TASK", fontSize = 18.sp,fontWeight = FontWeight.ExtraBold,color=Color.White)
                }
                CardTask(data = listOf("Crawl dữ liệu","Tạo đặc trưng mới","Đánh giá mô hình"),1)
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
                CardTask(data = listOf("Tạo file js","Reset css","Gắn link html"),1)
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
                CardTask(data = listOf("Đi ngủ","Ăn cơm","Tắm rửa"),1)
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
        Card(
            backgroundColor = Color(251, 249, 245),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTask(data = listOf("Python","Data Science","Java spring"),2)
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
                CardTask(data = listOf("Python","Data Science","Java spring"),3)
                Button(onClick = {
                    status.value = !status.value
                }, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(245, 62, 62))) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
                    Text(text = " DELETE ALL", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
                }
                if (status.value)
                    Row(modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 20.dp),
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
    LazyRow{
        items(data){
                item ->
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(14),
                    modifier = Modifier.padding(14.dp)) {
                    Column(
                    ) {
                        Text(text = item, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.Black)
                        Text(text = "Chạy Deadline nè !", fontSize = 14.sp, fontWeight = FontWeight.Light,color=Color.Black)
                    }
                }
        }
    }
}

//Dialog in TO-DO / DONE / DOING screen
@Composable
fun DialogInAllTaskScreen() {
            Row(modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            ButtonToDoTransfer()
            ButtonDoingTransfer()
            ButtonDoneTransfer()
        }
}

//Dialog in Archive Screen
@Composable
fun DialogInArchiveScreen() {
    Row(modifier = Modifier
        .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        ButtonUnarchive()
        ButtonDelete()
    }
}

////Dialog in Trash Screen
@Composable
fun DialogInTrashScreen() {
    Row(modifier = Modifier
        .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        ButtonDelete()
    }
}

//Card task
@Composable
fun CardTask(data: List<String>, index: Int) {
    val status = remember {
        mutableStateOf(false)
    }

    LazyRow{
        items(data){
                item ->
            Button(onClick = {status.value = !status.value},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(14),
                modifier = Modifier.padding(14.dp)) {
                Column(
                ) {
                    Text(text = item, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.Black)
                }
            }
        }
    }
    if (status.value && index == 1)
        DialogInAllTaskScreen()
    else if(status.value && index == 2)
        DialogInArchiveScreen()
    else if(status.value && index == 3)
        DialogInTrashScreen()
}
