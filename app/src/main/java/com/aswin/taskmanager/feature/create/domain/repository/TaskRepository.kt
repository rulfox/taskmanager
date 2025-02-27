package com.aswin.taskmanager.feature.create.domain.repository

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: Task): Result<String>
    suspend fun deleteTask(id: Int): Result<String>
    suspend fun updateTaskStatus(id: Int, status: Status): Result<String>
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByStatus(statuses: List<Status>): Flow<List<Task>>
}