package com.aswin.taskmanager.feature.details.data

import com.aswin.taskmanager.core.room.entity.Task

sealed class TaskDetailUiEvent {
    data object OnBackPressed: TaskDetailUiEvent()
    data class ShowError(val message: String) : TaskDetailUiEvent()
}