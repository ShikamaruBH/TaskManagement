package com.shikamarubh.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.shikamarubh.taskmanagement.view.*
import com.shikamarubh.taskmanagement.viewmodel.ProjectViewModel
import com.shikamarubh.taskmanagement.viewmodel.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val topBarTitle = remember {
                    mutableStateOf("Task management")
                }
                val navController = rememberNavController()
                val isDialogOpen = remember { mutableStateOf(false) }
                val currentRoute = navController
                    .currentBackStackEntryFlow
                    .collectAsState(initial = navController.currentBackStackEntry)

                Scaffold(
                    topBar = { TopAppBar(topBarTitle) },
                    floatingActionButton = {
                        when (currentRoute.value?.destination?.route) {
                            "addproject" -> {
                                FloatingActionButton(
                                    backgroundColor = colorResource(id = R.color.colorAddProject),
                                    onClick = {
                                        isDialogOpen.value = true
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "New project",
                                            tint = colorResource(id = R.color.black)
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
                                            imageVector = Icons.Filled.DeleteForever,
                                            contentDescription = "Delete all",
                                            tint = colorResource(id = R.color.colorPrimary),
                                            modifier = Modifier.size(29.dp)
                                        )
                                    }
                                )
                            }
                            else -> {
                                FloatingActionButton(
                                    backgroundColor = colorResource(id = R.color.colorAddTask),
                                    onClick = {
                                        isDialogOpen.value = true
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Filled.AddTask,
                                            contentDescription = "New task",
                                            tint = colorResource(id = R.color.colorPrimary),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                )
                            }
                        }
                    },
                    bottomBar = { BottomNavigation(navController = navController,topBarTitle=topBarTitle) }
                ) {
                    val projectViewModel = viewModel<ProjectViewModel>()
                    val taskViewModel = viewModel<TaskViewModel>()
                    Navigation(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        projectViewModel = projectViewModel,
                        isDialogOpen = isDialogOpen,
                        topBarTitle
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
    topBarTitle: MutableState<String>
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
                topBarTitle = topBarTitle,
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
                navController = navController,
                topBarTitle = topBarTitle
            )
        }
        composable(NavigationItem.Trash.route) {
            TrashScreen(
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                navController = navController,
                isDialogOpen = isDialogOpen,
                topBarTitle = topBarTitle
            )
        }
    }
}


@Composable
fun BottomNavigation(navController: NavHostController,
                     topBarTitle: MutableState<String>) {
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
                    topBarTitle.value = "Task management"
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
fun TopAppBar(topBarTitle: MutableState<String>) {
    TopAppBar(
        title = {
            Text(
                text = topBarTitle.value,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        backgroundColor = colorResource(id = R.color.colorTopBar),
    )
}