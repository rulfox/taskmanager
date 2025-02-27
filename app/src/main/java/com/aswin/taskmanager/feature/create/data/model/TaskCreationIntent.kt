package com.aswin.taskmanager.feature.create.data.model

import kotlinx.datetime.LocalDate

sealed class TaskCreationIntent {
    data class TitleChanged(val newTitle: String) : TaskCreationIntent()
    data class DescriptionChanged(val newDescription: String) : TaskCreationIntent()
    data class DueDateChanged(val newDueDate: LocalDate) : TaskCreationIntent()
    data class PriorityChanged(val newPriority: PriorityState) : TaskCreationIntent()
    data object CreateTask : TaskCreationIntent()
    data class ToggleDatePicker(val showDatePicker: Boolean): TaskCreationIntent()
    data object OnBackPressed: TaskCreationIntent()
}