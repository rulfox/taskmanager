package com.aswin.taskmanager.feature.details.data

import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskDetailState(
    var title: String = "",
    var taskId: Int = -1,
    var taskUiState: TaskUiState ?= null,
    var displayTaskDetail: Boolean = false,
)
