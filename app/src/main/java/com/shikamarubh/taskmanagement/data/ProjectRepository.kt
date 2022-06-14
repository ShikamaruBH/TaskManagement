package com.shikamarubh.taskmanagement.data

import com.shikamarubh.taskmanagement.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class ProjectRepository
    @Inject constructor(private val projectDao: ProjectDao) {
        fun getAllProjects() : Flow<List<Project>> = projectDao.getAllProjects().flowOn(Dispatchers.IO)
        fun getAllArchivedProjects() : Flow<List<Project>> = projectDao.getAllArchivedProjects().flowOn(Dispatchers.IO)
        fun getAllDeletedProjects() : Flow<List<Project>> = projectDao.getAllDeletedProjects().flowOn(Dispatchers.IO)
        suspend fun addProject(project: Project) = projectDao.insertProject(project)
        suspend fun updateProject(project: Project) = projectDao.updateProject(project)
        suspend fun deleteProject(project: Project) = projectDao.deleteProject(project)
        suspend fun deleteAllProjectsIsDeleted() = projectDao.deleteAllProjectsIsDeleted()
        suspend fun deleteAllProjects() = projectDao.deleteAllProjects()
        suspend fun archiveProject(project: Project) {
            project.isArchived = true;
            projectDao.updateProject(project)
        }
        suspend fun sortDeleteProject(project: Project) {
            project.isDeleted = true;
            projectDao.updateProject(project)
        }
        suspend fun unarchiveProject(project: Project) {
            project.isArchived = false;
            projectDao.updateProject(project)
        }
        suspend fun restoreProject(project: Project) {
            project.isDeleted = false;
            project.isArchived = false;
            projectDao.updateProject(project)
        }

}