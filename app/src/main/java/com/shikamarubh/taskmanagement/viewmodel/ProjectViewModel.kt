package com.shikamarubh.taskmanagement.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikamarubh.taskmanagement.data.ProjectRepository
import com.shikamarubh.taskmanagement.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel
    @Inject constructor(private val projectRepository: ProjectRepository) : ViewModel() {
        private val _projectList = MutableStateFlow<List<Project>>(emptyList())
        private val _archivedProjectList = MutableStateFlow<List<Project>>(emptyList())
        private val _deletedProjectList = MutableStateFlow<List<Project>>(emptyList())

        val projectList = _projectList.asStateFlow()
        val archivedProjectList = _archivedProjectList.asStateFlow()
        val deletedProjectList = _deletedProjectList.asStateFlow()

        init {
            viewModelScope.launch(Dispatchers.IO) {
                projectRepository.getAllProjects().distinctUntilChanged()
                    .collect { listOfProject ->
                        if (listOfProject.isNullOrEmpty()) {
                            Log.d("DEBUG", "No project")
                            _projectList.value = emptyList()
                        } else {
                            _projectList.value = listOfProject
                        }
                    }
            }
            viewModelScope.launch(Dispatchers.IO) {
                projectRepository.getAllArchivedProjects().distinctUntilChanged()
                    .collect { listOfProject ->
                        if (listOfProject.isNullOrEmpty()) {
                            Log.d("DEBUG", "No archived project")
                            _archivedProjectList.value = emptyList()
                        } else {
                            _archivedProjectList.value = listOfProject
                        }
                    }
            }
            viewModelScope.launch(Dispatchers.IO) {
                projectRepository.getAllDeletedProjects().distinctUntilChanged()
                    .collect {
                            listOfProject ->
                        if (listOfProject.isNullOrEmpty()) {
                            Log.d("DEBUG", "No deleted project")
                            _deletedProjectList.value = emptyList()
                        } else {
                            _deletedProjectList.value = listOfProject
                        }
                    }
            }
        }

        fun addProject(project: Project) = viewModelScope.launch { projectRepository.addProject(project) }
        fun updateProject(project: Project) = viewModelScope.launch { projectRepository.updateProject(project) }
        fun archiveProject(project: Project) = viewModelScope.launch { projectRepository.archiveProject(project) }
        fun unarchiveProject(project: Project) = viewModelScope.launch { projectRepository.unarchiveProject(project) }
        fun sortDeleteProject(project: Project) = viewModelScope.launch { projectRepository.sortDeleteProject(project) }
        fun restoreProject(project: Project) = viewModelScope.launch { projectRepository.restoreProject(project) }
        fun deleteProject(project: Project) = viewModelScope.launch { projectRepository.deleteProject(project) }
        fun deleteAllProjectsIsDeleted() = viewModelScope.launch { projectRepository.deleteAllProjectsIsDeleted() }
        fun deleteAllProjects() = viewModelScope.launch { projectRepository.deleteAllProjects() }
        fun getProjectById(id : UUID) : LiveData<Project> {
            val project = MutableLiveData<Project>()
            viewModelScope.launch {
                project.value = projectList.value.filter { p -> p.id == id }[0]
            }
            return project
        }
}