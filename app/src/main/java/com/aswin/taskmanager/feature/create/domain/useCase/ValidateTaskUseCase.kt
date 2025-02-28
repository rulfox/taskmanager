package com.aswin.taskmanager.feature.create.domain.useCase

import com.aswin.taskmanager.core.room.entity.Task
import javax.inject.Inject

/**
 * Use case responsible for validating a [Task] object.
 *
 * This use case checks if a given task is valid based on predefined criteria.
 * Currently, it only checks if the task's title is empty.
 *
 * @constructor Creates a [ValidateTaskUseCase] instance.
 */
class ValidateTaskUseCase @Inject constructor() {
    operator fun invoke(task: Task): Result<String>{
        if(task.title.isEmpty()){
            return Result.failure(Exception("Title cannot be empty"))
        }
        return Result.success("Task validated successfully")
    }
}