package com.aswin.taskmanager.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object TaskListScreen: Screen

    @Serializable
    data object TaskDetailScreen: Screen

    @Serializable
    data object TaskCreationScreen: Screen

    @Serializable
    data object SettingsScreen: Screen
}
