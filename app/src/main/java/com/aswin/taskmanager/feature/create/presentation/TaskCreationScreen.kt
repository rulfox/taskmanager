package com.aswin.taskmanager.feature.create.presentation

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aswin.taskmanager.R
import com.aswin.taskmanager.core.util.ComposeUtils.isPortrait
import com.aswin.taskmanager.core.util.showShortToast
import com.aswin.taskmanager.feature.create.data.model.TaskCreationIntent
import com.aswin.taskmanager.feature.create.data.model.TaskCreationState
import com.aswin.taskmanager.feature.create.data.model.TaskCreationUiEvent
import kotlinx.datetime.LocalDate

@Composable
fun TaskCreationScreen(viewModel: TaskCreationViewModel = hiltViewModel(), onTaskCreated: () -> Unit) {
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
                    onTaskCreated()
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
        onToggleDatePicker = { viewModel.processIntent(TaskCreationIntent.ToggleDatePicker(it)) }
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = state.title,
            onValueChange = taskCreationCallback.onTitleChanged,
            label = { Text(text = stringResource(R.string.title)) }, // Use string resources
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // Set the desired IME action
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.description?:"",
            onValueChange = taskCreationCallback.onDescriptionChanged,
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.description)
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))


        // Priority Dropdown
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                readOnly = true,
                value = state.priority.label,
                onValueChange = {  },
                label = { Text(stringResource(R.string.priority)) },
                trailingIcon = {
                    IconButton(onClick = {
                        expanded = !expanded
                    }) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                state.priorities.forEach { priority ->
                    DropdownMenuItem(text = {
                        Text(text = priority.label)
                    },
                    onClick = {
                        expanded = false
                        taskCreationCallback.onPriorityChanged(priority)
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Due Date Picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                readOnly = true,
                value = state.dueDate.toString(),
                onValueChange = {},
                label = { Text(stringResource(R.string.due_date)) },
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = {
                        taskCreationCallback.onToggleDatePicker(true)
                    }) {
                        Icon(imageVector = androidx.compose.material.icons.Icons.Filled.DateRange, contentDescription = "Select Date")
                    }
                }
            )

            if (state.showDatePicker) {
                DatePickerDialog(LocalContext.current, { _: DatePicker, selectedYear, selectedMonth, selectedDay ->
                    taskCreationCallback.apply {
                        onDateChanged(LocalDate(selectedYear, selectedMonth + 1, selectedDay))
                        onToggleDatePicker(false)
                    }
                }, state.dueDate.dayOfMonth, state.dueDate.monthNumber, state.dueDate.year).apply {
                    setOnCancelListener {
                        taskCreationCallback.onToggleDatePicker(false)
                    }
                    show()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = taskCreationCallback.onCreateTask,
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text(stringResource(R.string.create_task))
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