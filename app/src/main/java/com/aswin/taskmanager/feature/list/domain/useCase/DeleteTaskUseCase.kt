package com.aswin.taskmanager.feature.list.domain.useCase

import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Int) = taskRepository.deleteTask(id = id)
}