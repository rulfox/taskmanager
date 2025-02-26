package com.aswin.taskmanager.feature.list.data

import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import kotlinx.datetime.LocalDate

data class TaskUiState(
    val id: Long,
    val title: String,
    val description: String?,
    val priorityFormatted: String,
    val dueDateFormatted: String,
    val statusFormatted: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val status: Status
)
