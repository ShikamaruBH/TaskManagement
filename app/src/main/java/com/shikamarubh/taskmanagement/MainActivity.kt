package com.shikamarubh.taskmanagement

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar()},
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
///////////////////////////Ở DƯỚI KHÔNG CẦN ĐỌC///////////////////////////////////////

//Thanh top menu
@Composable
fun TopAppBar() {
    TopAppBar(
        title = { Text(text = "Task Manager App", fontWeight = FontWeight.Bold, color = colorResource(id = R.color.colorTodo)) },
        backgroundColor = colorResource(id = R.color.colorPrimary),
    )
}

//Button To-do in Card Transfer
@Composable
fun ButtonToDoTransfer() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorTodo)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "TO-DO", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Button Doing in Card Transfer
@Composable
fun ButtonDoingTransfer() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDoing)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "DOING", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Button Done in Card Transfer
@Composable
fun ButtonDoneTransfer() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDone)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "DONE", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Button Unarchive in Card Transfer
@Composable
fun ButtonUnarchive() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "UNARCHIVE", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Button Delete in Card Transfer
@Composable
fun ButtonDelete() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "DELETE", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Nút thêm dự án
@Composable
fun ButtonAddProject() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        Text(text = " ADD PROJECT", fontSize = 18.sp,fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Nút thêm task
@Composable
fun ButtonAddTask() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorAdd)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text("+", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
        Text(text = " ADD TASK", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Nút không đồng ý xoá dự án trong thùng rác
@Composable
fun ButtonDeclineDeleteAllProject() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "DECLINE", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
    }
}

//Nút đồng ý xoá dự án trong thùng rác
@Composable
fun ButtonConfirmDeleteAllProject() {
    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorConfirm)),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(14.dp)) {
        Text(text = "CONFIRM", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,color=Color.White)
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