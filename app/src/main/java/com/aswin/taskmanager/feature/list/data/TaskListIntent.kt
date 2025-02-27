package com.aswin.taskmanager.feature.list.data

sealed class TaskListIntent {
    data class OnTaskClicked(val taskUiState: TaskUiState) : TaskListIntent()
    data class OnTaskDeleted(val taskUiState: TaskUiState) : TaskListIntent()
    data class OnTaskCompleted(val taskUiState: TaskUiState) : TaskListIntent()
}