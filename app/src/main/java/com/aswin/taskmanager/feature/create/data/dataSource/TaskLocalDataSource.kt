package com.aswin.taskmanager.feature.create.data.dataSource

import androidx.room.Insert
import androidx.room.Query
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    suspend fun insertTask(task: Task): Long

    suspend fun deleteTask(id: Int): Int

    suspend fun updateTaskStatus(id: Int, status: Status): Int

    fun getAllTasks(): Flow<List<Task>>

    fun getTasksByStatus(status: Status): Flow<List<Task>>
}