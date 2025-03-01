package com.aswin.taskmanager.feature.list.data

import com.aswin.taskmanager.feature.create.data.model.PriorityState
import kotlinx.datetime.LocalDate

data class TaskListCallback (
    val onTaskClicked: (TaskUiState) -> Unit = {},
    val onCreateTaskClicked: () -> Unit = {},
    val onTaskDeleted:(TaskUiState) -> Unit = {},
    val onTaskCompleted: (TaskUiState) -> Unit = {},
    val onFilterApplied: (FilterStatus) -> Unit = {},
    val onFilterRequested: () -> Unit = {},
    val onFilterDismissRequested: () -> Unit = {}
)