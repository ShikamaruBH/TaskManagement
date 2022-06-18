package com.shikamarubh.taskmanagement.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shikamarubh.taskmanagement.data.ProjectRepository
import com.shikamarubh.taskmanagement.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel
    @Inject constructor(private val projectRepository: ProjectRepository) : ViewModel() {
        // Đối tượng firestore dùng để giao tiếp với Firestore
        private val db = Firebase.firestore
        // Đối tượng collection reference kết nối đến collection tương ứng trên Firestore
        private val collRef = db.collection("projects")
        private val auth = Firebase.auth
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

        fun addProject(project: Project) = viewModelScope.launch {
            if (auth.currentUser != null) {
                // Tạo document mới trong collection projects
                val docRef = collRef.document()
                project.userId = auth.currentUser!!.uid
                project.id = docRef.id
                // Gán data trong document mới tạo bằng đối tượng project
                docRef.set(project)
                projectRepository.addProject(project)
            }
        }
        fun updateProject(project: Project) = viewModelScope.launch { projectRepository.updateProject(project) }
        fun archiveProject(project: Project) = viewModelScope.launch {
            // Update trường isArchived với giá trị true trong collection projects (collRef) ở document có id bằng id của đối tượng project
            collRef.document(project.id).update("isArchived",true)
            projectRepository.archiveProject(project)
        }
        fun unarchiveProject(project: Project) = viewModelScope.launch { projectRepository.unarchiveProject(project) }
        fun sortDeleteProject(project: Project) = viewModelScope.launch { projectRepository.sortDeleteProject(project) }
        fun restoreProject(project: Project) = viewModelScope.launch { projectRepository.restoreProject(project) }
        fun deleteProject(project: Project) = viewModelScope.launch {
            // Xoá document trong collection projects mà có id bằng id của đối tượng project truyền vào
            collRef.document(project.id).delete()
            projectRepository.deleteProject(project)
        }
        fun deleteAllProject() = viewModelScope.launch {
            collRef.whereEqualTo("isDeleted",true).get()
                .addOnSuccessListener { result ->
                    db.runBatch { batch ->
                        for (doc in result) {
                            batch.delete(doc.reference)
                        }
                    }
                }
            projectRepository.deleteAllProject()
        }
        fun getProjectById(id : String) : LiveData<Project> {
            val project = MutableLiveData<Project>()
            viewModelScope.launch {
                project.value = projectList.value.filter { p -> p.id == id }[0]
            }
            return project
        }
}