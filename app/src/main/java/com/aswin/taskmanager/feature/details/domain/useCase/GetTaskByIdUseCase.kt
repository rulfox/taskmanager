package com.aswin.taskmanager.feature.details.domain.useCase

import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.domain.repository.TaskRepository
import javax.inject.Inject


/**
 * Use case for retrieving a task by its ID.
 *
 * This class encapsulates the logic for fetching a specific task from the underlying
 * [TaskRepository] based on the provided task ID. It utilizes the repository to
 * interact with the data source and returns the task as a [Result] object,
 * handling potential errors gracefully.
 *
 * @property taskRepository The repository responsible for providing access to task data.
 * @constructor Creates a [GetTaskByIdUseCase] instance with the provided [TaskRepository].
 */
class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Result<Task>{
        return taskRepository.getTaskById(id = id)
    }
}