package com.aswin.taskmanager.feature.create.data.repository

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.data.dataSource.TaskLocalDataSource
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource
): TaskRepository {
    override suspend fun createTask(task: Task): Result<String> {
        try {
            val taskCreatedResult = taskLocalDataSource.insertTask(task = task)
            if(taskCreatedResult == -1L) {
                return Result.failure(Exception("Task creation failed"))
            }
            return Result.success("Task created successfully")
        } catch(e: Exception) {
            Timber.e(e)
            return Result.failure(Exception("Task creation failed"))
        }
    }

    override suspend fun updateTaskStatus(id: Int, status: Status): Result<String> {
        try {
            val taskUpdatedResult = taskLocalDataSource.updateTaskStatus(id = id, status = status)
            if(taskUpdatedResult <= 0) {
                return Result.failure(Exception("Task update failed"))
            }
            return Result.success("Task updated successfully")
        } catch(e: Exception) {
            Timber.e(e)
            return Result.failure(Exception("Task update failed"))
        }

    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskLocalDataSource.getAllTasks()
    }

    override fun getTasksByStatus(status: Status): Flow<List<Task>> {
        return taskLocalDataSource.getTasksByStatus(status = status)
    }
}