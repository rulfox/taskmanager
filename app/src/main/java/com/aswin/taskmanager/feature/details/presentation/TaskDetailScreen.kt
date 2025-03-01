package com.aswin.taskmanager.feature.details.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aswin.taskmanager.R
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.core.util.ComposeUtils.isPortrait
import com.aswin.taskmanager.core.util.formatDate
import com.aswin.taskmanager.core.util.showShortToast
import com.aswin.taskmanager.feature.create.data.model.PriorityState
import com.aswin.taskmanager.feature.create.presentation.CreateTaskAppbar
import com.aswin.taskmanager.feature.create.presentation.PriorityDropDown
import com.aswin.taskmanager.feature.details.data.TaskDetailCallback
import com.aswin.taskmanager.feature.details.data.TaskDetailIntent
import com.aswin.taskmanager.feature.details.data.TaskDetailState
import com.aswin.taskmanager.feature.details.data.TaskDetailUiEvent
import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskDetailScreen(id: Int, viewModel: TaskDetailViewModel = hiltViewModel(), onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(id) {
        viewModel.processIntent(TaskDetailIntent.OnTaskIdReceived(id = id))
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event){
                TaskDetailUiEvent.OnBackPressed -> {
                    onBackPressed()
                }
                is TaskDetailUiEvent.ShowError -> {
                    context.showShortToast(message = event.message)
                    onBackPressed()
                }
            }
        }
    }
    val taskDetailCallback = TaskDetailCallback(
        onBackPressed = { viewModel.processIntent(TaskDetailIntent.OnBackPressed) }
    )

    if(isPortrait()){
        TaskDetailContentPortrait(state, taskDetailCallback)
    } else {
        TaskDetailContentLandscape(state, taskDetailCallback)
    }
}

@Composable
fun TaskDetailContentLandscape(
    state: TaskDetailState,
    taskDetailCallback: TaskDetailCallback
) {
    TaskDetailContentPortrait(state, taskDetailCallback)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailContentPortrait(
    state: TaskDetailState,
    taskDetailCallback: TaskDetailCallback
) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            CreateTaskAppbar(
                title = stringResource(R.string.app_name),
                onBackPressed = {
                    taskDetailCallback.onBackPressed()
                }
            )

            state.taskUiState?.let { taskUiState ->
                Column(
                    modifier = Modifier
                        .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 10.dp)
                                .requiredHeight(height = 38.dp)
                                .clip(shape = RoundedCornerShape(22.dp))
                                .background(color = taskUiState.priorityColor)
                        )
                        Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                            Text(
                                text = "Task ${taskUiState.id}",
                                color = Color(0xff6e6a7c),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = taskUiState.title,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.Start
                ) {

                    PriorityDropDown(
                        modifier = Modifier.fillMaxWidth().padding(top = 22.dp),
                        priorities = listOf(),
                        selectedPriorityState = PriorityState.getPriorityState(taskUiState.priority),
                        onPriorityStateChanged = {}
                    )

                    Card(
                        modifier = Modifier
                            .padding(top = 22.dp)
                            .clickable {

                            }
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(size = 15.dp))
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),

                        ) {
                            Image(
                                modifier = Modifier.padding(top = 4.dp),
                                painter = painterResource(id = R.drawable.rounded_calendar),
                                contentDescription = "Due Date"
                            )
                            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = "Due Date",
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                                    text = taskUiState.dueDate.formatDate(pattern = "d MMMM, yyyy"),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            if(taskUiState.isCompleted){
                                Box(
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .clip(shape = RoundedCornerShape(31.dp))
                                        .background(color = Color(0xff00A707).copy(alpha = 0.1f))
                                        .align(Alignment.CenterVertically)
                                ){
                                    Text(
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                        text = "Completed",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xff00A707)
                                        )
                                    )
                                }
                            } else if(taskUiState.isDue) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .clip(shape = RoundedCornerShape(31.dp))
                                        .background(color = Color(0xffF56D91).copy(alpha = 0.1f))
                                        .align(Alignment.CenterVertically)
                                ){
                                    Text(
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                        text = "Due",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xffF56D91)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if(taskUiState.showDescription){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth().padding(top = 22.dp)
                                .clip(shape = RoundedCornerShape(size = 15.dp))
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp)) {
                                Image(
                                    modifier = Modifier.padding(top = 4.dp),
                                    painter = painterResource(id = R.drawable.description),
                                    contentDescription = "Description"
                                )
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        modifier = Modifier.padding(start = 8.dp),
                                        text = "Description",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 8.dp),
                                        text = taskUiState.description?:"",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            } ?: run {
                //TODO Implement
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TaskDetailContentPortraitPreview() {
    TaskDetailContentPortrait(TaskDetailState(
        taskUiState = TaskUiState(
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
    ), TaskDetailCallback())
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskDetailContentLandscapePreview() {
    TaskDetailContentLandscape(TaskDetailState(), TaskDetailCallback())
}