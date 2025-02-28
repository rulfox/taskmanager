package com.aswin.taskmanager.feature.list.data

import androidx.compose.ui.graphics.Color
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import kotlinx.datetime.LocalDate

data class TaskUiState(
    val id: Int,
    val title: String,
    val description: String?,
    val priorityFormatted: String,
    val dueDateFormatted: String,
    val statusFormatted: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val status: Status,
    val showDescription: Boolean,
    val priorityColor: Color
)
