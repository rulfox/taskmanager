package com.aswin.taskmanager.feature.list.domain.useCase

import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() = taskRepository.getAllTasks()
}