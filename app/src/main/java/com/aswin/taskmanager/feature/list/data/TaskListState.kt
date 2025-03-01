package com.aswin.taskmanager.feature.list.data

import com.aswin.taskmanager.core.room.entity.Status

data class TaskListState(
    var showEmptyTasks: Boolean = false,
    var tasks: List<TaskUiState> = listOf(),
    var statuses: List<Status> = listOf(Status.PENDING, Status.COMPLETED),
    var selectedFilter: FilterStatus = FilterStatus.ALL,
    val showFilter: Boolean = false
)
