package com.aswin.taskmanager.feature.list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.util.ComposeUtils.isPortrait
import com.aswin.taskmanager.core.util.showShortToast
import com.aswin.taskmanager.feature.list.data.TaskListCallback
import com.aswin.taskmanager.feature.list.data.TaskListIntent
import com.aswin.taskmanager.feature.list.data.TaskListState
import com.aswin.taskmanager.feature.list.data.TaskListUiEvent
import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    onCreateTask: () -> Unit = {},
    onTaskDetail: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when(it){
                is TaskListUiEvent.OnTaskClicked -> {
                    onTaskDetail(it.taskUiState.id)
                }
                is TaskListUiEvent.ShowError -> {
                    context.showShortToast(it.message)
                }
            }
        }
    }

    val taskListingCallback = TaskListCallback(
        onTaskClicked = { viewModel.processIntent(TaskListIntent.OnTaskClicked(it)) },
        onTaskDeleted = { viewModel.processIntent(TaskListIntent.OnTaskDeleted(it)) },
        onTaskCompleted = { viewModel.processIntent(TaskListIntent.OnTaskCompleted(it)) },
        onCreateTaskClicked = { onCreateTask() },
        onFilterRequested = { viewModel.processIntent(TaskListIntent.ShowFilter) },
        onFilterApplied = { viewModel.processIntent(TaskListIntent.OnFilterApplied(it)) },
        onFilterDismissRequested = { viewModel.processIntent(TaskListIntent.OnFilterDismissRequested) },
    )

    if(isPortrait()){
        TaskListingContentPortrait(state, taskListingCallback)
    } else {
        TaskListingContentLandscape(state, taskListingCallback)
    }
}

@Composable
fun TaskListingContentLandscape(state: TaskListState, taskListingCallback: TaskListCallback) {
    TaskListingContentPortrait(state = state, taskListingCallback = taskListingCallback)
}

@Composable
fun TaskListingContentPortrait(state: TaskListState, taskListingCallback: TaskListCallback) {
    val deletedItem = remember { mutableStateOf<TaskUiState?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TaskManagerAppBar(
                onFilterRequested = {
                    taskListingCallback.onFilterRequested()
                }
            )
            LazyColumn(modifier = Modifier.fillMaxSize().weight(1f)) {
                items(
                    items = state.tasks,
                    key = { it.id }
                ) { taskUiState ->
                    SwipeBox(
                        onDelete = {
                            taskListingCallback.onTaskDeleted(taskUiState)
                        },
                        onComplete = {
                            taskListingCallback.onTaskCompleted(taskUiState)
                        },
                        modifier = Modifier.animateItem()
                    ){
                        TaskItem(taskUiState = taskUiState, onTaskClicked = taskListingCallback.onTaskClicked)
                    }
                }
            }
        }

        if(state.showEmptyTasks){
            EmptyTasksImagePlaceHolder(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        FloatingActionButton(
            onClick = taskListingCallback.onCreateTaskClicked,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
        }
        if(state.showFilter){
            FilterBottomSheet(
                selectedFilter = state.selectedFilter,
                onSelected = {
                    taskListingCallback.onFilterApplied(it)
                },
                onDismissRequest = {
                    taskListingCallback.onFilterDismissRequested()
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TaskListingContentPortraitPreviewLight() {
    TaskListingContentPortrait(state = TaskListState(
        tasks = listOf(
            TaskUiState(
                title = "Task 1",
                description = "Description",
                priority = Priority.HIGH,
                dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                status = Status.PENDING,
                dueDateFormatted = "Monday, Jan 15, 2024",
                priorityFormatted = "High",
                statusFormatted = "Pending",
                id = 1,
                showDescription = true,
                priorityColor = Color(0xfff99600),
                isCompleted = true,
                isDue = false
            )
        )
    ), taskListingCallback = TaskListCallback())
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskListingContentLandscapePreviewDark() {
    TaskListingContentLandscape(state = TaskListState(), taskListingCallback = TaskListCallback())
}