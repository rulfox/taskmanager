package com.aswin.taskmanager.feature.create.data.dataSource

import com.aswin.taskmanager.core.room.dao.TaskDao
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskLocalDataSource {
    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task = task)
    }

    override suspend fun deleteTask(id: Int): Int {
        return taskDao.deleteTask(id = id)
    }

    override suspend fun updateTaskStatus(id: Int, status: Status): Int {
        return taskDao.updateTaskStatus(id = id, status = status)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override fun getTasksByStatus(statuses: List<Status>): Flow<List<Task>> {
        return taskDao.getTasksByStatus(statuses = statuses)
    }
}