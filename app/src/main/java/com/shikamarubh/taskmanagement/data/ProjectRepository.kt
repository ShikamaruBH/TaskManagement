package com.shikamarubh.taskmanagement.data

import com.shikamarubh.taskmanagement.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProjectRepository
    @Inject constructor(private val projectDao: ProjectDao) {
        fun getUserProjects(userId: String) : Flow<List<Project>> = projectDao.getUserProjects(userId).flowOn(Dispatchers.IO)
        fun getAllProjects(userId: String) : Flow<List<Project>> = projectDao.getAllProjects(userId).flowOn(Dispatchers.IO)
        fun getAllArchivedProjects(userId: String) : Flow<List<Project>> = projectDao.getAllArchivedProjects(userId).flowOn(Dispatchers.IO)
        fun getAllDeletedProjects(userId: String) : Flow<List<Project>> = projectDao.getAllDeletedProjects(userId).flowOn(Dispatchers.IO)
        suspend fun addProject(project: Project) = projectDao.insertProject(project)
        suspend fun updateProject(project: Project) = projectDao.updateProject(project)
        suspend fun deleteProject(project: Project) = projectDao.deleteProject(project)
        suspend fun deleteAllProject(userId: String) = projectDao.deleteAllProject(userId)
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