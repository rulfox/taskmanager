package com.aswin.taskmanager.feature.details.data

import com.aswin.taskmanager.core.navigation.Screen

sealed class TaskDetailIntent {
    data class OnTaskIdReceived(val id: Int): TaskDetailIntent()
    data object OnBackPressed: TaskDetailIntent()
}