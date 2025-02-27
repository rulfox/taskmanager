package com.aswin.taskmanager.feature.create.data.model

/**
 * Sealed class to represent the different UI events that can occur in the Task Creation screen.
 */
sealed class TaskCreationUiEvent {
    data class TaskCreated(val message: String) : TaskCreationUiEvent()
    data class ShowError(val message: String) : TaskCreationUiEvent()
    data object OnBackPressed: TaskCreationUiEvent()
}