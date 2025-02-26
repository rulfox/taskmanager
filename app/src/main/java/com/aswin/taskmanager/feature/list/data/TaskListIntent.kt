package com.aswin.taskmanager.feature.list.data

sealed class TaskListIntent {
    data class OnTaskClicked(val taskUiState: TaskUiState) : TaskListIntent()
}