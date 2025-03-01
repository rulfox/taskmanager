package com.aswin.taskmanager.feature.create.presentation

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aswin.taskmanager.R
import com.aswin.taskmanager.core.util.ComposeUtils.isPortrait
import com.aswin.taskmanager.core.util.formatDate
import com.aswin.taskmanager.core.util.showShortToast
import com.aswin.taskmanager.feature.create.data.model.TaskCreationIntent
import com.aswin.taskmanager.feature.create.data.model.TaskCreationState
import com.aswin.taskmanager.feature.create.data.model.TaskCreationUiEvent
import kotlinx.datetime.LocalDate

@Composable
fun TaskCreationScreen(viewModel: TaskCreationViewModel = hiltViewModel(), onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event){
                is TaskCreationUiEvent.ShowError -> {
                    context.showShortToast(message = event.message)
                }
                is TaskCreationUiEvent.TaskCreated -> {
                    context.showShortToast(message = event.message)
                    onBackPressed()
                }
                TaskCreationUiEvent.OnBackPressed -> {
                    onBackPressed()
                }
            }
        }
    }
    val taskCreationCallback = TaskCreationCallback(
        onTitleChanged = { viewModel.processIntent(TaskCreationIntent.TitleChanged(it)) },
        onDescriptionChanged = { viewModel.processIntent(TaskCreationIntent.DescriptionChanged(it)) },
        onPriorityChanged = { viewModel.processIntent(TaskCreationIntent.PriorityChanged(it)) },
        onDateChanged = { viewModel.processIntent(TaskCreationIntent.DueDateChanged(it)) },
        onCreateTask = { viewModel.processIntent(TaskCreationIntent.CreateTask) },
        onToggleDatePicker = { viewModel.processIntent(TaskCreationIntent.ToggleDatePicker(it)) },
        onBackPressed = { viewModel.processIntent(TaskCreationIntent.OnBackPressed) }
    )

    if(isPortrait()){
        TaskCreationContentPortrait(state, taskCreationCallback)
    } else {
        TaskCreationContentLandscape(state, taskCreationCallback)
    }
}

@Composable
fun TaskCreationContentLandscape(
    state: TaskCreationState,
    taskCreationCallback: TaskCreationCallback
) {
    TaskCreationContentPortrait(state, taskCreationCallback)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreationContentPortrait(
    state: TaskCreationState,
    taskCreationCallback: TaskCreationCallback
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CreateTaskAppbar(
                title = stringResource(R.string.app_name),
                onBackPressed = {
                    taskCreationCallback.onBackPressed()
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                val focusRequester = remember { FocusRequester() }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create Task",
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp)
                        .clip(shape = RoundedCornerShape(size = 15.dp))
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp)) {
                        Image(
                            modifier = Modifier.padding(top = 4.dp),
                            painter = painterResource(id = R.drawable.task_icon),
                            contentDescription = "Task Icon"
                        )
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Task Name",
                                style = MaterialTheme.typography.labelSmall
                            )
                            BasicTextField(
                                value = state.title,
                                onValueChange = taskCreationCallback.onTitleChanged,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                                decorationBox = { innerTextField ->
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)) {
                                        innerTextField()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                            )
                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }
                        }
                    }
                }

                PriorityDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    priorities = state.priorities,
                    selectedPriorityState = state.priority,
                    onPriorityStateChanged = taskCreationCallback.onPriorityChanged
                )

                Card(
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .clickable {
                            taskCreationCallback.onToggleDatePicker(true)
                        }
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 15.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
                    ) {
                        Image(
                            modifier = Modifier.padding(top = 4.dp),
                            painter = painterResource(id = R.drawable.rounded_calendar),
                            contentDescription = "Due Date"
                        )
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Due Date",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp),
                                text = state.dueDate.formatDate(pattern = "d MMMM, yyyy"),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Image(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.rounded_arrow_drop_down),
                            contentDescription = "Due Date"
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp)
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
                            BasicTextField(
                                value = state.description?:"",
                                onValueChange = taskCreationCallback.onDescriptionChanged,
                                textStyle = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                                decorationBox = { innerTextField ->
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 80.dp)
                                        .padding(start = 8.dp)) {
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    shape = RoundedCornerShape(size = 15.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = taskCreationCallback.onCreateTask,
                    enabled = true
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = stringResource(R.string.create_task),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        if (state.showDatePicker) {
            val initialYear = state.dueDate.year
            val initialMonth = state.dueDate.monthNumber - 1
            val initialDay = state.dueDate.dayOfMonth

            DatePickerDialog(
                LocalContext.current,
                { _: DatePicker, year: Int, month: Int, day: Int ->
                    val selectedDate = LocalDate(year, month + 1, day)
                    taskCreationCallback.onDateChanged(selectedDate)
                    taskCreationCallback.onToggleDatePicker(false)
                },
                initialYear,
                initialMonth,
                initialDay
            ).apply {
                setOnCancelListener {
                    taskCreationCallback.onToggleDatePicker(false)
                }
                show()
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TaskCreationContentPortraitPreview() {
    TaskCreationContentPortrait(TaskCreationState(), TaskCreationCallback())
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskCreationContentLandscapePreview() {
    TaskCreationContentLandscape(TaskCreationState(), TaskCreationCallback())
}