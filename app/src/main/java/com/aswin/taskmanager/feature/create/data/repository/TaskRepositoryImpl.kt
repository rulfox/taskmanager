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

    override suspend fun deleteTask(id: Int): Result<String> {
        try {
            val taskDeletedResult = taskLocalDataSource.deleteTask(id = id)
            if(taskDeletedResult > 0) {
                return Result.success("Task deleted successfully")
            }
            return Result.failure(Exception("Task deletion failed"))
        } catch(e: Exception) {
            Timber.e(e)
            return Result.failure(Exception("Task deletion failed"))
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

    override suspend fun getTaskById(id: Int): Result<Task> {
        try {
            val taskGetResult = taskLocalDataSource.getTaskById(id = id)
            taskGetResult?.let {
                return Result.success(it)
            }?: run {
                return Result.failure(Exception("No task found"))
            }
        } catch(e: Exception) {
            Timber.e(e)
            return Result.failure(Exception("Task details fetching failed"))
        }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskLocalDataSource.getAllTasks()
    }

    override fun getTasksByStatus(statuses: List<Status>): Flow<List<Task>> {
        return taskLocalDataSource.getTasksByStatus(statuses = statuses)
    }
}