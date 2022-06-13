package com.shikamarubh.taskmanagement

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.shikamarubh.taskmanagement.model.Project
import com.shikamarubh.taskmanagement.model.Status
import com.shikamarubh.taskmanagement.model.Task
import com.shikamarubh.taskmanagement.viewmodel.ProjectViewModel
import com.shikamarubh.taskmanagement.viewmodel.TaskViewModel
import java.util.*

//**************ALL SCREENS**************
@Composable
fun ProjectScreen(
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                val listProject by projectViewModel.projectList.collectAsState()
                CardProject(
                    projectViewModel = projectViewModel,
                    listProject = listProject,
                    navController = navController
                )
                CallAlertDialog(
                    1,
                    taskViewModel,
                    projectViewModel,
                    null,
                    navController,
                    isDialogOpen
                )
            }
        }
    }
}

//To do screen
//
@Composable
@SuppressLint("StateFlowValueCalledInComposition")
fun ToDoScreen(
    id: String?,
    navController: NavController,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    isDialogOpen: MutableState<Boolean>,
) {
    val id_Task = UUID.fromString(id)
    val allToDoTask = taskViewModel.getToDoTasksByProjectId(id_Task).value
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.colorPrimary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogInAllTaskScreen(id_Task, navController, "TODO")
            CardTask(
                listTask = allToDoTask,
                taskViewModel = taskViewModel,
                navController = navController
            )
            CallAlertDialog(
                2,
                taskViewModel,
                projectViewModel,
                id_Task,
                navController,
                isDialogOpen,
            )
        }

    }
}

//DoneScreen
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DoneScreen(
    id: String?,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>,
) {
    val id_Task = UUID.fromString(id)
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
                DialogInAllTaskScreen(id_Task, navController, "DONE")
                val allDoneTask = taskViewModel.getDoneTasksByProjectId(id_Task).value
                CardTask(
                    listTask = allDoneTask,
                    taskViewModel = taskViewModel,
                    navController = navController
                )
                CallAlertDialog(
                    2,
                    taskViewModel,
                    projectViewModel,
                    id_Task,
                    navController,
                    isDialogOpen
                )
            }
        }
    }
}

//DoingScreen
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DoingScreen(
    id: String?,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>,
) {
    val id_Task = UUID.fromString(id)
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
                DialogInAllTaskScreen(id_Task, navController, "DOING")
                val allDoingTask = taskViewModel.getDoingTasksByProjectId(id_Task).value
                CardTask(
                    listTask = allDoingTask,
                    taskViewModel = taskViewModel,
                    navController = navController
                )
                CallAlertDialog(
                    2,
                    taskViewModel,
                    projectViewModel,
                    id_Task,
                    navController,
                    isDialogOpen
                )
            }
        }
    }
}

//Archive
@Composable
fun ArchiveScreen(
    projectViewModel: ProjectViewModel,
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Card(
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Archive Screen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Text(
                    text = "Hold card to choose desired actions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                val listProject by projectViewModel.archivedProjectList.collectAsState()
                CardArchiveScreen(
                    projectViewModel = projectViewModel,
                    listProject = listProject,
                    navController = navController
                )
            }
        }
    }
}

//TrashScreen
@Composable
fun TrashScreen(
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Card(
            backgroundColor = colorResource(id = R.color.colorPrimary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Trash Screen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "Hold card to choose desired actions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
                val listProject by projectViewModel.deletedProjectList.collectAsState()
                CardTrashScreen(
                    projectViewModel = projectViewModel,
                    listProject = listProject,
                    navController = navController
                )
                if (listProject.isNotEmpty()) {
                    CallAlertDialog(
                        3,
                        taskViewModel,
                        projectViewModel,
                        null,
                        navController,
                        isDialogOpen,
                    )
                }

            }
        }
    }
}
//**************ALL SCREENS**************


//**************Dialog about Project Screen - Add Task - Confirm Delete**************
@Composable
fun CallAlertDialog(
    index: Int,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    id: UUID?,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (index) {
            1 -> {
                ShowProjectDialog(isDialogOpen, projectViewModel)
            }
            2 -> {
                if (id != null) {
                    ShowTaskDialog(isDialogOpen, taskViewModel, id, navController)
                }
            }
            3 -> {
                ShowConfirmDeleteDialog(isDialogOpen, projectViewModel)
            }
        }
    }
}


@Composable
fun ShowProjectDialog(isDialogOpen: MutableState<Boolean>, projectViewModel: ProjectViewModel) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(5.dp),
                color = colorResource(id = R.color.colorPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var textName by remember { mutableStateOf("") }
                    var textDescription by remember { mutableStateOf("") }
                    Text(
                        text = "Add new project",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            onClick = { isDialogOpen.value = false },
                            content = {
                                Text(
                                    text = "CANCEL",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xfffd3c7e)
                                )
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                        TextButton(
                            onClick = {
                                projectViewModel.addProject(
                                    Project(
                                        title = textName,
                                        description = textDescription,
                                        isDeleted = false,
                                        isArchived = false
                                    )
                                )
                                isDialogOpen.value = false
                            },
                            content = {
                                Text(
                                    text = "ADD PROJECT",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xfffd3c7e)
                                )
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowTaskDialog(
    isDialogOpen: MutableState<Boolean>,
    taskViewModel: TaskViewModel,
    id: UUID,
    navController: NavController
) {
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
                    val mCheckedImportant = remember { mutableStateOf(false) }
                    Row {

                        Text(
                            text = "Important",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Switch(
                            checked = mCheckedImportant.value,
                            onCheckedChange = { mCheckedImportant.value = it })
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            onClick = { isDialogOpen.value = false },
                            content = {
                                Text(
                                    text = "CANCEL",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xfffd3c7e)
                                )
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                        TextButton(
                            onClick = {
                                taskViewModel.addTask(
                                    Task(
                                        content = textTask,
                                        status = Status.TODO,
                                        isImportant = mCheckedImportant.value,
                                        projectId = id
                                    )
                                )
                                navController.navigate(NavigationItem.ToDo.withArgs(id.toString()))
                                isDialogOpen.value = false
                            },
                            content = {
                                Text(
                                    text = "ADD TASK",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xfffd3c7e)
                                )
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun ShowConfirmDeleteDialog(
    isDialogOpen: MutableState<Boolean>,
    projectViewModel: ProjectViewModel
) {
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
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Do you want to delete?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ButtonConfirmDeleteAllProject(
                            projectViewModel = projectViewModel,
                            isDialogOpen = isDialogOpen
                        )
                        ButtonDeclineDeleteAllProject(isDialogOpen = isDialogOpen)
                    }
                }
            }
        }
    }
}
//**************Dialog about Project - Task - Confirm**************

//**************Dialog about Task Screen**************
//Card task
@Composable
fun CardTask(listTask: List<Task>, taskViewModel: TaskViewModel, navController: NavController) {
//    val isDialogOpen = remember { mutableStateOf(false) }
    LazyColumn {
        items(listTask) { item ->
//                CallAlertTaskOrArchiveScreen(1, item)
            Box(
                contentAlignment = Alignment.Center
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    var color = Color.White
                    if (item.isImportant) {
                        color = colorResource(id = R.color.colorConfirm)
                    }
//                    ShowTaskScreenDialog(isDialogOpen,item.id,taskViewModel,navController)
                    Button(
                        onClick = {
                            expanded = true
//                            isDialogOpen.value = true
//                            Log.d("test","ID ${item.id}")
//                            Log.d("test","ID${item.content}")
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = color),
                        shape = RoundedCornerShape(14),
                        modifier = Modifier.padding(
                            start = 5.dp,
                            bottom = 2.dp,
                            top = 1.dp,
                            end = 5.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = item.content,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                }

                DropdownMenu(
                    modifier = Modifier.align(Alignment.Center),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    val statusTask = item.status
                    val projectID = item.projectId

                    if (statusTask != Status.TODO) {
                        DropdownMenuItem(onClick = {
                            taskViewModel.toToDo(item)
                            navController.navigate(NavigationItem.ToDo.withArgs(item.projectId.toString()))
                            expanded = false
                        }) {
                            Text(
                                "Move to ToDo",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                            )
                        }
                    }

                    if (statusTask != Status.DOING) {
                        DropdownMenuItem(onClick = {
                            taskViewModel.toDoing(item)
                            navController.navigate(NavigationItem.Doing.withArgs(item.projectId.toString()))
                            expanded = false
                        }) {
                            Text(
                                "Move to Doing",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                            )
                        }
                    }

                    if (statusTask != Status.DONE) {
                        DropdownMenuItem(onClick = {
                            taskViewModel.toDone(item)
                            navController.navigate(NavigationItem.Done.withArgs(item.projectId.toString()))
                            expanded = false
                        }) {
                            Text(
                                "Move to Done",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                            )
                        }
                    }


                    if (item.isImportant) {
                        DropdownMenuItem(onClick = {
                            taskViewModel.makeTaskNormal(item)
                            expanded = false
                        }) {
                            Text(
                                "Make normal",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                            )
                        }
                    } else {
                        DropdownMenuItem(onClick = {
                            taskViewModel.makeTaskImportance(item)
                            expanded = false
                        }) {
                            Text(
                                "Make important",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                            )
                        }
                    }

                    DropdownMenuItem(onClick = {
                        taskViewModel.deleteTask(item)
                        when (statusTask) {
                            Status.TODO -> {
                                navController.navigate(NavigationItem.ToDo.withArgs(projectID.toString()))
                            }
                            Status.DOING -> {
                                navController.navigate(NavigationItem.Doing.withArgs(projectID.toString()))
                            }
                            Status.DONE -> {
                                navController.navigate(NavigationItem.Done.withArgs(projectID.toString()))
                            }
                        }
                        expanded = false
                    }) {
                        Text(
                            "Delete",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}
//**************Dialog about Task Screen**************

//**************Dialog about Archive Screen**************
//Card task
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardArchiveScreen(
    projectViewModel: ProjectViewModel,
    listProject: List<Project>,
    navController: NavController
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(180.dp)
    ) {
        items(listProject) { item ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Surface(
                    modifier = Modifier
                        .padding(14.dp)
                        .size(width = 200.dp, height = 70.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navController.navigate(NavigationItem.ToDo.withArgs(item.id.toString()))
                                },
                                onLongPress = {
                                    expanded = true
                                }
                            )
                        },
                    elevation = 4.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(14),

                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = item.description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }

                DropdownMenu(
                    modifier = Modifier.align(Alignment.Center),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(onClick = {
                        projectViewModel.unarchiveProject(item)
                        expanded = false
                    }) {
                        Text(
                            text = "Restore",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                    DropdownMenuItem(onClick = {
                        projectViewModel.sortDeleteProject(item)
                        expanded = false
                    }) {
                        Text(
                            "Move to trash",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}
//**************Dialog about Archive Screen**************

//**************Dialog about Trash Screen**************
//Card task
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardTrashScreen(
    projectViewModel: ProjectViewModel,
    listProject: List<Project>,
    navController: NavController
) {
    LazyVerticalGrid(cells = GridCells.Adaptive(180.dp)) {
        items(listProject) { item ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Surface(
                    modifier = Modifier
                        .padding(14.dp)
                        .size(width = 200.dp, height = 70.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navController.navigate(NavigationItem.ToDo.withArgs(item.id.toString()))
                                },
                                onLongPress = {
                                    expanded = true
                                }
                            )
                        },
                    elevation = 4.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(14),

                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = item.description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }

                DropdownMenu(
                    modifier = Modifier.align(Alignment.Center),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(onClick = {
                        projectViewModel.restoreProject(item)
                        expanded = false
                    }) {
                        Text(
                            "Restore",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                    DropdownMenuItem(onClick = {
                        projectViewModel.deleteProject(item)
                        expanded = false
                    }) {
                        Text(
                            "Permanently Delete",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}
//**************Dialog about Trash Screen**************

//**************About Project Screen**************
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardProject(
    projectViewModel: ProjectViewModel,
    listProject: List<Project>,
    navController: NavController
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(180.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(listProject) { item ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Surface(
                    modifier = Modifier
                        .padding(14.dp)
                        .size(width = 200.dp, height = 70.dp)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navController.navigate(NavigationItem.ToDo.withArgs(item.id.toString()))
//                                        navController.navigate(NavigationItem.Test.withArgs(item.id.toString()))
                                },
                                onLongPress = {
                                    expanded = true
                                }
                            )
                        },
                    elevation = 4.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(14),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = item.description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }

                DropdownMenu(
                    modifier = Modifier.align(Alignment.Center),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(onClick = {
                        projectViewModel.archiveProject(item)
                        expanded = false
                    }) {
                        Text(
                            "Archive",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                    DropdownMenuItem(onClick = {
                        projectViewModel.sortDeleteProject(item)
                        expanded = false
                    }) {
                        Text(
                            "Delete",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                }
            }

        }
    }
}
//**************About Project Screen**************

//**************All Buttons**************

@Composable
fun DividerTask() {
    Divider(
        Modifier
            .width(90.dp)
            .padding(start = 20.dp), thickness = 4.dp
    )
}

@Composable
fun DialogInAllTaskScreen(id: UUID, navController: NavController, type: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            ButtonToDoTransfer(id, navController)
            if (type == "TODO") {
                DividerTask()
            }
        }
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            ButtonDoingTransfer(id, navController)
            if (type == "DOING") {
                DividerTask()
            }
        }
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            ButtonDoneTransfer(id, navController)
            if (type == "DONE") {
                DividerTask()
            }
        }
    }
}

//**************All Buttons In Task Screen**************
@Composable
fun ButtonToDoTransfer(id: UUID, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(NavigationItem.ToDo.withArgs(id.toString()))
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorTodo)),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(5.dp)
            .width(100.dp)
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
fun ButtonDoingTransfer(id: UUID, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(NavigationItem.Doing.withArgs(id.toString()))
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDoing)),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(5.dp)
            .width(100.dp)
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
fun ButtonDoneTransfer(id: UUID, navController: NavController) {
    Button(
        onClick = {
            navController.navigate(NavigationItem.Done.withArgs(id.toString()))
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDone)),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(5.dp)
            .width(100.dp)
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

//**************All Buttons In Trash Screen**************
@Composable
fun ButtonDeclineDeleteAllProject(isDialogOpen: MutableState<Boolean>) {
    Button(
        onClick = {
            isDialogOpen.value = false
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorDecline)),
        shape = RoundedCornerShape(30),
        modifier = Modifier.padding(5.dp)
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
fun ButtonConfirmDeleteAllProject(
    projectViewModel: ProjectViewModel,
    isDialogOpen: MutableState<Boolean>
) {
    Button(
        onClick = {
            projectViewModel.deleteAllProjectsIsDeleted()
            isDialogOpen.value = false
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorConfirm)),
        shape = RoundedCornerShape(30),
        modifier = Modifier.padding(5.dp)
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
