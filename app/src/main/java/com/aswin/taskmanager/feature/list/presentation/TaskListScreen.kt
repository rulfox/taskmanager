package com.aswin.taskmanager.feature.list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.util.ComposeUtils.isPortrait
import com.aswin.taskmanager.feature.list.data.TaskListCallback
import com.aswin.taskmanager.feature.list.data.TaskListIntent
import com.aswin.taskmanager.feature.list.data.TaskListState
import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    onCreateTask: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val taskListingCallback = TaskListCallback(
        onTaskClicked = { viewModel.processIntent(TaskListIntent.OnTaskClicked(it)) },
        onCreateTaskClicked = { onCreateTask() }
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
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.tasks) { taskUiState ->
                TaskItem(taskUiState = taskUiState, onTaskClicked = taskListingCallback.onTaskClicked)
            }
        }

        FloatingActionButton(
            onClick = taskListingCallback.onCreateTaskClicked,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
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
                id = 1
            )
        )
    ), taskListingCallback = TaskListCallback())
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskListingContentLandscapePreviewDark() {
    TaskListingContentLandscape(state = TaskListState(), taskListingCallback = TaskListCallback())
}