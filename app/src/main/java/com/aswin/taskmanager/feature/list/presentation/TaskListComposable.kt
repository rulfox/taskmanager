package com.aswin.taskmanager.feature.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.list.data.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskItem(taskUiState: TaskUiState, onTaskClicked: (TaskUiState) -> Unit = {}){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTaskClicked(taskUiState) },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = taskUiState.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = taskUiState.statusFormatted,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = taskUiState.dueDateFormatted,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TTaskItemPreview() {
    TaskItem(taskUiState = TaskUiState(
        title = "Task 1",
        description = "Description",
        priority = Priority.HIGH,
        dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        status = Status.PENDING,
        dueDateFormatted = "Monday, Jan 15, 2024",
        priorityFormatted = "High",
        statusFormatted = "Pending",
        id = 1
    ))
}