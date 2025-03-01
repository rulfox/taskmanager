package com.aswin.taskmanager.feature.details.data

import com.aswin.taskmanager.feature.create.data.model.PriorityState
import kotlinx.datetime.LocalDate

data class TaskDetailCallback (
    val onBackPressed: () -> Unit = {}
)