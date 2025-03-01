package com.aswin.taskmanager.feature.details.presentation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.details.data.TaskDetailIntent
import com.aswin.taskmanager.feature.details.data.TaskDetailState
import com.aswin.taskmanager.feature.details.data.TaskDetailUiEvent
import com.aswin.taskmanager.feature.details.domain.useCase.GetTaskByIdUseCase
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
class TaskDetailViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val taskUiStateMapper: TaskUiStateMapper
) : ViewModel() {
    private var _state = MutableStateFlow(TaskDetailState())
    val state: StateFlow<TaskDetailState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<TaskDetailUiEvent>()
    val event: SharedFlow<TaskDetailUiEvent> = _event.asSharedFlow()

    private fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTaskByIdUseCase(id = id).onSuccess {
                _state.value = _state.value.copy(taskUiState = taskUiStateMapper.map(it))
            }.onFailure {
                _event.emit(TaskDetailUiEvent.ShowError(it.message?:"Something went wrong"))
            }
        }
    }

    fun processIntent(intent: TaskDetailIntent){
        when(intent){
            TaskDetailIntent.OnBackPressed -> {
                viewModelScope.launch {
                    _event.emit(TaskDetailUiEvent.OnBackPressed)
                }
            }
            is TaskDetailIntent.OnTaskIdReceived -> {
                _state.value = _state.value.copy(taskId = intent.id)
                getTask(id = intent.id)
            }
        }
    }
}