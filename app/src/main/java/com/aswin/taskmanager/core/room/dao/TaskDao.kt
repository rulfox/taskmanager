package com.aswin.taskmanager.core.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task): Long

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Int): Int

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE status IN (:statuses)")
    fun getTasksByStatus(statuses: List<Status>): Flow<List<Task>>

    @Query("UPDATE tasks SET status = :status WHERE id = :id")
    suspend fun updateTaskStatus(id: Int, status: Status): Int

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?
}