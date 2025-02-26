package com.aswin.taskmanager.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val priority: Priority,
    val dueDate: LocalDate,
    val status: Status = Status.PENDING
)