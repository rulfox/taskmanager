package com.aswin.taskmanager.feature.list.domain.useCase

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Int, status: Status) = taskRepository.updateTaskStatus(id = id, status = status)
}