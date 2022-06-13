package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
                val isDialogOpen = remember { mutableStateOf(false) }
                val currentRoute = navController
                    .currentBackStackEntryFlow
                    .collectAsState(initial = navController.currentBackStackEntry)
                Scaffold(
                    topBar = { TopAppBar() },
                    floatingActionButton = {
                        when (currentRoute.value?.destination?.route) {
                            "addproject" -> {
                                FloatingActionButton(
                                    backgroundColor = colorResource(id = R.color.colorAdd),
                                    onClick = {
                                        isDialogOpen.value = true

                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = " ",
                                            tint = colorResource(id = R.color.colorPrimary)
                                        )
                                    }
                                )
                            }
                            "archive" -> {
                            }
                            "trash" -> {
                                FloatingActionButton(
                                    backgroundColor = colorResource(id = R.color.colorConfirm),
                                    onClick = {
                                        isDialogOpen.value = true
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = " ",
                                            tint = colorResource(id = R.color.colorPrimary)
                                        )
                                    }
                                )
                            }
                            else -> {
                                FloatingActionButton(
                                    backgroundColor = colorResource(id = R.color.colorAdd),
                                    onClick = {
                                        isDialogOpen.value = true

                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = " ",
                                            tint = colorResource(id = R.color.colorPrimary)
                                        )
                                    }
                                )
                            }
                        }
                    },
                    bottomBar = { BottomNavigation(navController = navController) }
                ) {
                    val projectViewModel = viewModel<ProjectViewModel>()
                    val taskViewModel = viewModel<TaskViewModel>()
                    Navigation(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        projectViewModel = projectViewModel,
                        isDialogOpen = isDialogOpen
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
    projectViewModel: ProjectViewModel,
    isDialogOpen: MutableState<Boolean>,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.AddProject.route
    ) {
        composable(NavigationItem.AddProject.route) {
            ProjectScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController,
                isDialogOpen = isDialogOpen,
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
                projectViewModel = projectViewModel,
                isDialogOpen = isDialogOpen,
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
                projectViewModel = projectViewModel,
                isDialogOpen = isDialogOpen,
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
                projectViewModel = projectViewModel,
                isDialogOpen = isDialogOpen,
            )
        }
        composable(NavigationItem.Archive.route) {
            ArchiveScreen(
                projectViewModel = projectViewModel,
                navController = navController
            )
        }
        composable(NavigationItem.Trash.route) {
            TrashScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController,
                isDialogOpen = isDialogOpen,
            )
        }
    }
}


@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        NavigationItem.AddProject,
        NavigationItem.Archive,
        NavigationItem.Trash,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
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