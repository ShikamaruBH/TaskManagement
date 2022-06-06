package com.shikamarubh.taskmanagement

import java.util.*

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object AddProject: NavigationItem("addproject",R.drawable.ic_baseline_add_24,"Project")
    object ToDo: NavigationItem("todo",R.drawable.ic_baseline_add_24,"TO-DO")
    object Doing: NavigationItem("doing",R.drawable.ic_baseline_add_24,"DOING")
    object Done: NavigationItem("done",R.drawable.ic_baseline_add_24,"DONE")
    object Archive: NavigationItem("archive",R.drawable.ic_baseline_add_24,"ARCHIVE")
    object Trash: NavigationItem("trash",R.drawable.ic_baseline_add_24,"TRASH")
    object Test: NavigationItem("test",R.drawable.ic_baseline_add_24,"TEST")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {
                arg ->
                append("/$arg")
            }
        }
    }
}

