package com.aswin.taskmanager.feature.create.domain.useCase

import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<String>{
        return taskRepository.createTask(task = task)
    }
}