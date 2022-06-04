package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navController = navController)}
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = NavigationItem.AddProject.route){
        composable(NavigationItem.AddProject.route){
            ProjectScreen()
        }
        composable(NavigationItem.ToDo.route){
            ToDoScreen()
        }
        composable(NavigationItem.Doing.route){
            DoingScreen()
        }
        composable(NavigationItem.Done.route){
            DoneScreen()
        }
        composable(NavigationItem.Archive.route){
            ArchiveScreen()
        }
        composable(NavigationItem.Trash.route){
            TrashScreen()
        }
    }
}

@Composable
fun  BottomNavigation(navController: NavHostController){
    var items = listOf(
        NavigationItem.AddProject,
        NavigationItem.ToDo,
        NavigationItem.Doing,
        NavigationItem.Done,
        NavigationItem.Archive,
        NavigationItem.Trash,
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{
                item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let{
                                route -> popUpTo(route){
                            saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title)},
                label = { Text(text = item.title)},
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true
            )
        }
    }
}

//Card đồng ý xoá hay không
@Composable
fun CardConfirmDelete() {
    val paddingModifier = Modifier.padding(5.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(14),
        modifier = paddingModifier,
        backgroundColor = Color(251, 249, 245),
    ) {
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

//Card chuyển task sang TO-DO / DONE / DOING
@Composable
fun CardTransfer() {
    val paddingModifier = Modifier.padding(5.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(14),
        modifier = paddingModifier,
        backgroundColor = Color(251, 249, 245),
    ) {
        Row(modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            ButtonToDoTransfer()
            ButtonDoingTransfer()
            ButtonDoneTransfer()
        }
    }
}

//Card dự án
@Composable
fun CardProject() {
    val paddingModifier = Modifier.padding(10.dp)
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

//Text-field Task
@Composable
fun TextFieldTask() {
    Card(
        backgroundColor = Color(251, 249, 245),
    ) {
        Column(
            modifier = Modifier.padding(50.dp),
        ){
            var textTask by remember { mutableStateOf(" ") }
            OutlinedTextField(
                modifier = Modifier.padding(vertical = 20.dp),
                value = textTask,
                onValueChange = { textTask = it },
                label = { Text("Name Task", fontSize = 14.sp, fontWeight = FontWeight.Bold,color=Color.Black) }
            )
            ButtonAddTask()
        }
    }
}

///////////////////////////Ở DƯỚI KHÔNG CẦN ĐỌC///////////////////////////////////////

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

//Button To-do in Card Transfer
@Composable
fun ButtonToDoTransfer() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(125, 117, 109)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "TO-DO ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
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

//Button Doing in Card Transfer
@Composable
fun ButtonDoingTransfer() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(245, 160, 62)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DOING ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
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

//Button Done in Card Transfer
@Composable
fun ButtonDoneTransfer() {
    val paddingModifier = Modifier.padding(10.dp)
    val expanded = remember { mutableStateOf(false)}
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = Color(139, 192, 106)
    ) {
        Row(modifier = paddingModifier) {
            Text(text = "DONE ", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        }
    }
}

//Nút thêm dự án
@Composable
fun ButtonAddProject() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(98, 98, 246))) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold,color=Color.White)
        Text(text = " ADD PROJECT",color=Color.Black)
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

//Nút không đồng ý xoá dự án trong thùng rác
@Composable
fun ButtonDeclineDeleteAllProject() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(245, 62, 62))) {
        Text(text = "DECLINE",color=Color.White, fontWeight = FontWeight.Bold)
    }
}

//Nút đồng ý xoá dự án trong thùng rác
@Composable
fun ButtonConfirmDeleteAllProject() {
    Button(onClick = {}, shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(192,192,192))) {
        Text(text = "CONFIRM",color=Color.White, fontWeight = FontWeight.Bold)
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