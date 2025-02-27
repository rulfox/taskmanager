package com.aswin.taskmanager.feature.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.list.data.TaskListIntent
import com.aswin.taskmanager.feature.list.data.TaskListState
import com.aswin.taskmanager.feature.list.data.TaskListUiEvent
import com.aswin.taskmanager.feature.list.domain.mapper.TaskUiStateMapper
import com.aswin.taskmanager.feature.list.domain.useCase.GetAllTasksUseCase
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
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val taskUiStateMapper: TaskUiStateMapper
) : ViewModel() {
    private var _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<TaskListUiEvent>()
    val event: SharedFlow<TaskListUiEvent> = _event.asSharedFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTasksUseCase().collectLatest { tasks ->
                tasks.map { task ->
                    taskUiStateMapper.map(task = task)
                }.let {
                    _state.value = _state.value.copy(tasks = it)
                }
            }
        }
    }

    fun processIntent(intent: TaskListIntent){
        when(intent){
            is TaskListIntent.OnTaskClicked -> {

            }
            is TaskListIntent.OnTaskDeleted -> {
                val mutableTasks = _state.value.tasks.toMutableList()
                if (mutableTasks.remove(intent.taskUiState)) {
                    _state.value = _state.value.copy(tasks = mutableTasks)
                }
            }

            is TaskListIntent.OnTaskCompleted -> {
                val updatedTasks = _state.value.tasks.map { task ->
                    if (task.id == intent.taskUiState.id) {
                        task.copy(status = Status.COMPLETED)
                    } else {
                        task
                    }
                }
                _state.value = _state.value.copy(tasks = updatedTasks)
            }
        }
    }
}