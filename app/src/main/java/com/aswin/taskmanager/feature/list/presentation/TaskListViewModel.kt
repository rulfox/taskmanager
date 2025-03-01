package com.aswin.taskmanager.feature.list.presentation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.list.data.FilterStatus
import com.aswin.taskmanager.feature.list.data.TaskListIntent
import com.aswin.taskmanager.feature.list.data.TaskListState
import com.aswin.taskmanager.feature.list.data.TaskListUiEvent
import com.aswin.taskmanager.feature.list.domain.mapper.TaskUiStateMapper
import com.aswin.taskmanager.feature.list.domain.useCase.DeleteTaskUseCase
import com.aswin.taskmanager.feature.list.domain.useCase.GetAllTasksUseCase
import com.aswin.taskmanager.feature.list.domain.useCase.GetTasksByStatusUseCase
import com.aswin.taskmanager.feature.list.domain.useCase.UpdateTaskStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val getTasksByStatusUseCase: GetTasksByStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val taskUiStateMapper: TaskUiStateMapper
) : ViewModel() {
    private var _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<TaskListUiEvent>()
    val event: SharedFlow<TaskListUiEvent> = _event.asSharedFlow()

    init {
        observeTasks(statuses = state.value.statuses)
    }

    private fun observeTasks(statuses: List<Status>) {
        viewModelScope.launch(Dispatchers.IO) {
            getTasksByStatusUseCase(statuses).collectLatest { tasks ->
                tasks.map { task ->
                    taskUiStateMapper.map(task = task)
                }.let {
                    _state.value = _state.value.copy(tasks = it, showEmptyTasks = it.isEmpty())
                }
            }
        }
    }

    fun processIntent(intent: TaskListIntent){
        when(intent){
            is TaskListIntent.OnTaskClicked -> {
                viewModelScope.launch {
                    _event.emit(TaskListUiEvent.OnTaskClicked(intent.taskUiState))
                }
            }
            is TaskListIntent.OnTaskDeleted -> {
                viewModelScope.launch {
                    deleteTaskUseCase(id = intent.taskUiState.id)
                }
                /*val mutableTasks = _state.value.tasks.toMutableList()
                if (mutableTasks.remove(intent.taskUiState)) {
                    _state.value = _state.value.copy(tasks = mutableTasks)
                }*/
            }
            is TaskListIntent.OnTaskCompleted -> {
                viewModelScope.launch {
                    updateTaskStatusUseCase(id = intent.taskUiState.id, status = Status.COMPLETED)
                    /*val updatedTasks = _state.value.tasks.map { task ->
                        if (task.id == intent.taskUiState.id) {
                            task.copy(status = Status.COMPLETED)
                        } else {
                            task
                        }
                    }
                    _state.value = _state.value.copy(tasks = updatedTasks)*/
                }
            }
            is TaskListIntent.OnFilterApplied -> {
                val statuses = when(intent.filterStatus){
                    FilterStatus.ALL -> {
                        listOf(Status.PENDING, Status.COMPLETED)
                    }
                    FilterStatus.PENDING -> {
                        listOf(Status.PENDING)
                    }
                    FilterStatus.COMPLETED -> {
                        listOf(Status.COMPLETED)
                    }
                }
                _state.value = _state.value.copy(statuses = statuses)
                observeTasks(statuses = statuses)
            }
        }
    }
}