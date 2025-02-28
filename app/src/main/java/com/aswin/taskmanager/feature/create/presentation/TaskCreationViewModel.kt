package com.aswin.taskmanager.feature.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.feature.create.data.model.TaskCreationIntent
import com.aswin.taskmanager.feature.create.data.model.TaskCreationState
import com.aswin.taskmanager.feature.create.data.model.TaskCreationUiEvent
import com.aswin.taskmanager.feature.create.domain.useCase.CreateTaskUseCase
import com.aswin.taskmanager.feature.create.domain.useCase.ValidateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val validateTaskUseCase: ValidateTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(TaskCreationState())
    val state: StateFlow<TaskCreationState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<TaskCreationUiEvent>()
    val event: SharedFlow<TaskCreationUiEvent> = _event.asSharedFlow()

    fun processIntent(taskCreationIntent: TaskCreationIntent){
        when(taskCreationIntent){
            TaskCreationIntent.CreateTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val task = Task(
                        title = _state.value.title,
                        description = _state.value.description,
                        priority = _state.value.priority.level,
                        dueDate = _state.value.dueDate
                    )
                    validateTaskUseCase(task = task).onFailure {
                        _event.emit(TaskCreationUiEvent.ShowError(message = it.message?:"Failed to create task"))
                    }.onSuccess {
                        createTaskUseCase(task = task).onSuccess {
                            _event.emit(TaskCreationUiEvent.TaskCreated(message = it))
                        }.onFailure {
                            _event.emit(TaskCreationUiEvent.ShowError(it.message ?: "Failed to create task"))
                        }
                    }
                }
            }
            is TaskCreationIntent.DescriptionChanged -> {
                _state.value = _state.value.copy(description = taskCreationIntent.newDescription)
            }
            is TaskCreationIntent.DueDateChanged -> {
                _state.value = _state.value.copy(dueDate = taskCreationIntent.newDueDate)
            }
            is TaskCreationIntent.PriorityChanged -> {
                _state.value = _state.value.copy(priority = taskCreationIntent.newPriority)
            }
            is TaskCreationIntent.TitleChanged -> {
                _state.value = _state.value.copy(title = taskCreationIntent.newTitle)
            }
            is TaskCreationIntent.ToggleDatePicker -> {
                _state.value = _state.value.copy(showDatePicker = taskCreationIntent.showDatePicker)
            }
            TaskCreationIntent.OnBackPressed -> {
                viewModelScope.launch {
                    _event.emit(TaskCreationUiEvent.OnBackPressed)
                }
            }
        }
    }
}