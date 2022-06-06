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
import androidx.compose.ui.window.Dialog

//**************ALL SCREENS**************
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
                CardProject(data = listOf("Python", "Data Science", "Java spring"))
                CallAlertDialog(1, "ADD PROJECT")
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
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogInAllTaskScreen()
                CardTask(data = listOf("Crawl dữ liệu", "Tạo đặc trưng mới", "Đánh giá mô hình"))
                CallAlertDialog(2, "ADD TASK")
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
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogInAllTaskScreen()
                CardTask(data = listOf("Tạo file js", "Reset css", "Gắn link html"))
                CallAlertDialog(2, "ADD TASK")
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
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogInAllTaskScreen()
                CardTask(data = listOf("Đi ngủ", "Ăn cơm", "Tắm rửa"))
                CallAlertDialog(2, "ADD TASK")
            }
        }
    }
}

//Archive
@Composable
fun ArchiveScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        Card(
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Archive Screen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "Click into card to choose desirable actions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                CardArchiveScreen(data = listOf("Python", "Data Science", "Java spring"))
            }
        }
    }
}

//TrashScreen
@Composable
fun TrashScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
    ) {
        Card(
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Trash Screen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "Click into card to choose desirable actions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                CardTrashScreen(data = listOf("Python", "Data Science", "Java spring"))
                CallAlertDialog(3,"DELETE ALL")
            }
        }
    }
}
//**************ALL SCREENS**************


//**************Dialog about Project Screen - Add Task - Confirm Delete**************
@Composable
fun CallAlertDialog(index: Int, nameButton: String) {
    val isDialogOpen = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (index == 1) {
            ShowProjectDialog(isDialogOpen)
            Button(
                onClick = {
                    isDialogOpen.value = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(14.dp)
            ) {
                Text("+ ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(
                    text = nameButton,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        } else if (index == 2) {
            ShowTaskDialog(isDialogOpen)
            Button(
                onClick = {
                    isDialogOpen.value = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(14.dp)
            ) {
                Text("+ ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(
                    text = nameButton,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        } else if (index == 3) {
            ShowConfirmDeleteDialog(isDialogOpen)
            Button(
                onClick = {
                    isDialogOpen.value = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(14.dp)
            ) {
                Text("- ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(
                    text = nameButton,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ShowProjectDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(330.dp)
                    .height(400.dp)
                    .padding(15.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = " ADD PROJECT NAME",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
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
                    Spacer(modifier = Modifier.padding(5.dp))
                    var textDescription by remember { mutableStateOf(" ") }
                    OutlinedTextField(
                        modifier = Modifier.padding(vertical = 10.dp),
                        value = textDescription,
                        onValueChange = { textDescription = it },
                        label = {
                            Text(
                                "Project Description",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            "+",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = " ADD PROJECT",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowTaskDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(330.dp)
                    .height(400.dp)
                    .padding(15.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = " ADD TASK NAME",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    var textTask by remember { mutableStateOf(" ") }
                    OutlinedTextField(
                        modifier = Modifier.padding(vertical = 20.dp),
                        value = textTask,
                        onValueChange = { textTask = it },
                        label = {
                            Text(
                                "Task Name",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            "+",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = " ADD TASK",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowConfirmDeleteDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(400.dp)
                    .height(150.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonConfirmDeleteAllProject()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDeclineDeleteAllProject()
                }
            }
        }
    }
}
//**************Dialog about Project - Task - Confirm**************

//**************Dialog about Task Screen**************
//Card task
@Composable
fun CardTask(data: List<String>) {
    LazyRow {
        items(data) { item ->
            CallAlertTaskOrArchiveScreen(1, item)
        }
    }
}

@Composable
fun CallAlertTaskOrArchiveScreen(index: Int, item: String) {
    val isDialogOpen = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (index == 1)
            ShowTaskScreenDialog(isDialogOpen)
        else if (index == 2)
            ShowArchiveDialog(isDialogOpen)
        else if (index == 3)
            ShowTrashDialog(isDialogOpen)
        Button(
            onClick = {
                isDialogOpen.value = true
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(14),
            modifier = Modifier.padding(14.dp)
        ) {
            Column(
            ) {
                Text(
                    text = item,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun ShowTaskScreenDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(450.dp)
                    .height(150.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonToDoTransfer()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDoingTransfer()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDoneTransfer()
                }
            }
        }
    }
}
//**************Dialog about Task Screen**************

//**************Dialog about Archive Screen**************
//Card task
@Composable
fun CardArchiveScreen(data: List<String>) {
    LazyRow {
        items(data) { item ->
            CallAlertTaskOrArchiveScreen(2, item)
        }
    }
}

@Composable
fun ShowArchiveDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(400.dp)
                    .height(150.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonUnarchive()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDelete()
                }
            }
        }
    }
}
//**************Dialog about Archive Screen**************

//**************Dialog about Trash Screen**************
//Card task
@Composable
fun CardTrashScreen(data: List<String>) {
    LazyRow {
        items(data) { item ->
            CallAlertTaskOrArchiveScreen(3, item)
        }
    }
}

@Composable
fun ShowTrashDialog(isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(650.dp)
                    .height(150.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonToDoTransfer()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDoingTransfer()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDoneTransfer()
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonDelete()
                }
            }
        }
    }
}
//**************Dialog about Trash Screen**************

//**************About Project Screen**************
@Composable
fun CardProject(data: List<String>) {
    LazyRow {
        items(data) { item ->
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(14),
                modifier = Modifier.padding(14.dp)
            ) {
                Column(
                ) {
                    Text(
                        text = item,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "Chạy Deadline nè !",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
//**************About Project Screen**************

//**************All Buttons**************
@Composable
fun DialogInAllTaskScreen() {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonToDoTransfer()
        ButtonDoingTransfer()
        ButtonDoneTransfer()
    }
}

//**************All Buttons In Task Screen**************
@Composable
fun ButtonToDoTransfer() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorTodo)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "TO-DO",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}

@Composable
fun ButtonDoingTransfer() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDoing)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "DOING",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}

@Composable
fun ButtonDoneTransfer() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDone)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "DONE",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}
//**************All Buttons In Task Screen**************

//**************All Buttons In Archive Screen**************
@Composable
fun ButtonUnarchive() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "UNARCHIVE",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}

@Composable
fun ButtonDelete() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "DELETE",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}
//**************All Buttons In Archive Screen**************

//**************All Buttons In Trash Screen**************
@Composable
fun ButtonDeclineDeleteAllProject() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "DECLINE",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}

@Composable
fun ButtonConfirmDeleteAllProject() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorConfirm)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)
    ) {
        Text(
            text = "CONFIRM",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}
//**************All Buttons In Trash Screen**************

//**************All Buttons**************

