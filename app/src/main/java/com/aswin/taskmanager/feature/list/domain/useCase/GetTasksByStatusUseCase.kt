package com.aswin.taskmanager.feature.list.domain.useCase

import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksByStatusUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(statuses: List<Status>) = taskRepository.getTasksByStatus(statuses = statuses)
}