package com.aswin.taskmanager.feature.create.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskCreationState(
    var title: String = "",
    var description: String ?= null,
    var priority: PriorityState = PriorityState.MEDIUM,
    var dueDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,

    var priorities: List<PriorityState> = listOf(
        PriorityState.LOW, PriorityState.MEDIUM, PriorityState.HIGH
    ),
    var showDatePicker: Boolean = false
)
