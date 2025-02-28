package com.aswin.taskmanager.feature.create.domain.useCase

import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Use case for creating a new task.
 *
 * This class encapsulates the logic for creating a task and interacting with the
 * [TaskRepository] to persist the task. It's designed to be used in the domain layer
 * to separate business logic from data access concerns.
 *
 * @property taskRepository The repository responsible for managing task data.
 */
class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<String>{
        return taskRepository.createTask(task = task)
    }
}