package com.aswin.taskmanager.feature.list.data

/**
 * Sealed class to represent the different UI events that can occur in the Task Creation screen.
 */
sealed class TaskListUiEvent {
    data class OnTaskClicked(val taskUiState: TaskUiState): TaskListUiEvent()
    data class ShowError(val message: String) : TaskListUiEvent()
}