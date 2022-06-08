package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shikamarubh.taskmanagement.viewmodel.ProjectViewModel
import com.shikamarubh.taskmanagement.viewmodel.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar() },
                    bottomBar = { BottomNavigation(navController = navController) }
                ) {
                    val projectViewModel = viewModel<ProjectViewModel>()
                    val taskViewModel = viewModel<TaskViewModel>()

                    Navigation(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        projectViewModel = projectViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.AddProject.route
    ) {
        composable(NavigationItem.AddProject.route) {
            ProjectScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController
            )
        }
        composable(
            route = NavigationItem.ToDo.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )

        ) { entry ->
            ToDoScreen(
                id = entry.arguments?.getString("id"),
                navController = navController,
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel
            )
        }

        composable(
            route = NavigationItem.Doing.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )

        ) { entry ->
            DoingScreen(
                id = entry.arguments?.getString("id"),
                navController = navController,
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel
            )
        }

        composable(
            route = NavigationItem.Done.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )

        ) { entry ->
            DoneScreen(
                id = entry.arguments?.getString("id"),
                navController = navController,
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel
            )
        }
        composable(NavigationItem.Archive.route) {
            ArchiveScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController
            )
        }
        composable(NavigationItem.Trash.route) {
            TrashScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    var items = listOf(
        NavigationItem.AddProject,
//        NavigationItem.ToDo,
//        NavigationItem.Doing,
//        NavigationItem.Done,
        NavigationItem.Archive,
        NavigationItem.Trash,
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) {
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
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
    val expanded = remember { mutableStateOf(false) }
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(14),
        modifier = paddingModifier,
    ) {
        Column(modifier = paddingModifier) {
            Text(
                text = "Khoa học dữ liệu",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Text(
                text = "Chạy Deadline nè !",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }
    }
}
///////////////////////////Ở DƯỚI KHÔNG CẦN ĐỌC///////////////////////////////////////

//Thanh top menu
@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Task Manager App",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.colorTodo)
            )
        },
        backgroundColor = colorResource(id = R.color.colorPrimary),
    )
}