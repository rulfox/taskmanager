package com.aswin.taskmanager.feature.create.data.dataSource

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    suspend fun insertTask(task: Task): Long

    suspend fun deleteTask(id: Int): Int

    suspend fun updateTaskStatus(id: Int, status: Status): Int

    suspend fun getTaskById(id: Int): Task?

    fun getAllTasks(): Flow<List<Task>>

    fun getTasksByStatus(statuses: List<Status>): Flow<List<Task>>
}