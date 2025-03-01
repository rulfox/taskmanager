package com.aswin.taskmanager.feature.list.domain.mapper

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.data.model.PriorityState.Companion.getPriorityState
import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.datetime.toJavaLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class TaskUiStateMapper @Inject constructor() {
    private val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    fun map(task: Task): TaskUiState {
        return TaskUiState(
            id = task.id,
            title = task.title,
            description = task.description?:"",
            priority = task.priority,
            dueDate = task.dueDate,
            status = task.status,
            priorityFormatted = getPriorityState(priority = task.priority).label,
            dueDateFormatted = task.dueDate.toJavaLocalDate().format(formatter),
            statusFormatted = task.status.name,
            showDescription = !task.description.isNullOrEmpty(),
            priorityColor = getPriorityState(priority = task.priority).color,
            isCompleted = (task.status == Status.COMPLETED),
            isDue = LocalDate.now().isAfter(task.dueDate.toJavaLocalDate())
        )
    }
}