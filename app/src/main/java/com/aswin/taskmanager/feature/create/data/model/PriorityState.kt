package com.aswin.taskmanager.feature.create.data.model

import androidx.compose.ui.graphics.Color
import com.aswin.taskmanager.core.room.entity.Priority

sealed class PriorityState(val level: Priority, val label: String, val color: Color) {
    data object LOW: PriorityState(level = Priority.LOW, label = "Low", color = Color.Yellow)
    data object MEDIUM: PriorityState(level = Priority.MEDIUM, label = "Medium", color = Color.Blue)
    data object HIGH: PriorityState(level = Priority.HIGH, label = "High", color = Color.Red)

    companion object {
        fun getPriorityState(priority: Priority): PriorityState {
            return when (priority) {
                Priority.LOW -> LOW
                Priority.MEDIUM -> MEDIUM
                Priority.HIGH -> HIGH
            }
        }
    }
}