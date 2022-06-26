package com.shikamarubh.taskmanagement

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.shikamarubh.taskmanagement.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.shikamarubh.taskmanagement.model.Project
import com.shikamarubh.taskmanagement.model.Task
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
                val isLoggedIn = remember {
                    mutableStateOf(false)
                }
                val navController = rememberNavController()
                val isDialogOpen = remember { mutableStateOf(false) }
                val currentRoute = navController
                    .currentBackStackEntryFlow
                    .collectAsState(initial = navController.currentBackStackEntry)

                if (isLoggedIn.value) {
                    app(topBarTitle,currentRoute,isDialogOpen,navController,isLoggedIn)
                } else {
                    loginScreen(isLoggedIn = isLoggedIn)
                }
            }
        }
    }
}

@Composable
fun app(
    topBarTitle: MutableState<String>,
    currentRoute: State<NavBackStackEntry?>,
    isDialogOpen: MutableState<Boolean>,
    navController: NavHostController,
    isLoggedIn: MutableState<Boolean>,
    )
{
    Scaffold(
        topBar = { TopAppBar(topBarTitle) },
        drawerContent = { DrawerView(isLoggedIn) },
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
        syncDatabase(LocalContext.current,projectViewModel,taskViewModel)
        Navigation(
            navController = navController,
            taskViewModel = taskViewModel,
            projectViewModel = projectViewModel,
            isDialogOpen = isDialogOpen,
            topBarTitle
        )
    }
}
// Đọc dữ liệu người dùng từ firestore và kiểm tra đã có trong máy chưa
fun syncDatabase(context:Context,projectViewModel: ProjectViewModel, taskViewModel: TaskViewModel) {
    // Gọi refresh để cập nhật lại userId và các danh sách project sau khi người dùng mới đăng nhập vào

    var projectCount = 0
    var taskCount = 0
    projectViewModel.refresh()
    // Đọc các project có userId là Id của user đang đăng nhập
    projectViewModel.collRef
        .whereEqualTo("userId", projectViewModel.userId)
        .get()
        .addOnSuccessListener {
            result ->
            run { for (doc in result) {
                val project = doc.toObject<Project>()
                // Kiểm tra nếu chưa có trong CSDL trong máy thì lưu vào máy
                if (!projectViewModel.userProjects.value.contains(project)) {
                    projectViewModel.addProject(project)
                    projectCount ++ ;
                    Log.d("DEBUG", "Add project ${project.id} to local db")
                } else {
                    Log.d("DEBUG", "Project ${project.id} already in db")
                }
                taskViewModel.collRef
                    .whereEqualTo("projectId", project.id)
                    .get()
                    .addOnSuccessListener { result ->
                        for (doc in result) {
                            val task = doc.toObject<Task>()
                            // Kiểm tra nếu chưa có trong CSDL trong máy thì lưu vào máy
                            if (!taskViewModel.taskList.value.contains(task)) {
                                taskViewModel.addTask(task)
                                taskCount ++ ;
                                Log.d("DEBUG", "Add task ${task.id} to local db")
                            } else {
                                Log.d("DEBUG", "Task ${task.id} already in db")
                            }
                        }

                    }
                    .addOnCompleteListener{
                        Toast
                            .makeText(
                                context,
                                "Đã đọc $projectCount Project và $taskCount Task từ Firebase",
                                Toast.LENGTH_SHORT)
                            .show()
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
                id = entry.arguments?.getString("id")!!,
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
                id = entry.arguments?.getString("id")!!,
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
                id = entry.arguments?.getString("id")!!,
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


@Composable
fun loginScreen(isLoggedIn: MutableState<Boolean>) {
    var context = LocalContext.current
    val email = remember {
        mutableStateOf("")
    }
    val pass = remember {
        mutableStateOf("")
    }
    val pass2 = remember {
        mutableStateOf("")
    }
    val registering = remember {
        mutableStateOf(false)
    }
    val auth = Firebase.auth
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Task,
            tint = colorResource(id = R.color.colorProject),
            contentDescription = "logo",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp))
        Text(
            text = "Task management",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 100.dp))
        loginTextField(text = email, label = "Email", placeholder = "Nhập email",Icons.Filled.Email,false)
        loginTextField(text = pass, label = "Mật khẩu", placeholder = "Nhập mật khẩu",Icons.Filled.Lock)
        if (registering.value) {
            loginTextField(text = pass2, label = "Nhập lại mật khẩu", placeholder = "Nhập lại mật khẩu",Icons.Filled.Lock)
            Button(
                onClick = {
                    if (pass.value == pass2.value) {
                        auth.createUserWithEmailAndPassword(email.value,pass.value).addOnCompleteListener {
                                task ->
                            if (task.isSuccessful) {
                                isLoggedIn.value = true
                                Log.d("DEBUG", "Create new user successful")
                                Toast
                                    .makeText(
                                        context,
                                        "Đăng ký thành công",
                                        Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else {
                                Log.d("DEBUG", "Create new user fail")
                                Toast
                                    .makeText(
                                        context,
                                        "Đăng ký thất bại",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }

                    }

                }
                ,
                content = { Text(
                    text = "Đăng ký",
                    fontWeight = FontWeight.Bold
                ) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 100.dp, end = 100.dp, top = 10.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffec7b01)))
            TextButton(
                onClick = { registering.value = false },
                content = { Text(text = "Đăng nhập") },
                modifier = Modifier.padding(10.dp))
        } else {
            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(email.value,pass.value).addOnCompleteListener {
                            task ->
                        if (task.isSuccessful) {
                            isLoggedIn.value = true
                            Log.d("DEBUG", "Logging successful")
                            Toast
                                .makeText(
                                    context,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                        else{
                            Log.d("DEBUG", "Logging fail")
                            Toast
                                .makeText(
                                    context,
                                    "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                },
                content = { Text(
                    text = "Đăng nhập",
                    fontWeight = FontWeight.Bold
                ) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 100.dp, end = 100.dp, top = 10.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffec7b01)))
            TextButton(
                onClick = { registering.value = true },
                content = { Text(text = "Đăng ký") },
                modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun loginTextField(text: MutableState<String>, label: String, placeholder: String, icon: ImageVector, hideText: Boolean = true) {
    TextField(
        modifier = Modifier.padding(bottom = 10.dp),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = { Icon(imageVector = icon, contentDescription = "email") },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        )
    )
}

@Composable
fun AddDrawerHeader() {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp),
                    imageVector = Icons.Filled.Task,
                    contentDescription = "menu item",
                    tint = colorResource(id = R.color.colorProject))
                Text(
                    text = "Menu",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold)
            }
        })
}
@Composable
fun DrawerView(isLoggedIn: MutableState<Boolean>) {
    LazyColumn {
        item {
            AddDrawerHeader()
        }
        item {
            AddDrawerContentView(title = "Logout",icon = Icons.Filled.Logout,isLoggedIn)
        }
    }

}
@Composable
fun AddDrawerContentView(title: String, icon: ImageVector,isLoggedIn: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                Firebase.auth.signOut()
                isLoggedIn.value = false
            }
            .padding(10.dp)
            .fillMaxWidth(),
        content = {
            Icon(
                modifier = Modifier
                    .padding(10.dp),
                imageVector = icon,
                contentDescription = title)
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    )
}