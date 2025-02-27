package com.aswin.taskmanager.feature.create.presentation

import com.aswin.taskmanager.feature.create.data.model.PriorityState
import kotlinx.datetime.LocalDate

data class TaskCreationCallback (
    val onTitleChanged: (String) -> Unit = {},
    val onDescriptionChanged: (String) -> Unit = {},
    val onPriorityChanged: (PriorityState) -> Unit = {},
    val onDateChanged: (LocalDate) -> Unit = {},
    val onCreateTask: () -> Unit = {},
    val onToggleDatePicker: (Boolean) -> Unit = {},
    val onBackPressed: () -> Unit = {}
)