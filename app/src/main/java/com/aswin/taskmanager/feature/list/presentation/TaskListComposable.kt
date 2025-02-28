package com.aswin.taskmanager.feature.list.presentation

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aswin.taskmanager.R
import com.aswin.taskmanager.core.room.entity.Priority
import com.aswin.taskmanager.core.room.entity.Status
import com.aswin.taskmanager.feature.list.data.FilterStatus
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
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(top = 9.dp, end = 16.dp)
                    .clip(shape = RoundedCornerShape(31.dp))
                    .background(color = Color(0xfff0f0f0))
                    .align(alignment = Alignment.TopEnd)
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                    text = taskUiState.dueDateFormatted,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(modifier = Modifier.fillMaxWidth()){
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 26.dp, start = 16.dp, end = 16.dp)
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
                                text = "Task 1",
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
                    if(taskUiState.showDescription){
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            text = "Description",
                            color = Color(0xff6e6a7c),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            text = taskUiState.description?:"",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    /*Text(
                        text = taskUiState.statusFormatted,
                        style = MaterialTheme.typography.bodySmall
                    )*/
                }
            }
        }
    }
}

@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    lateinit var icon: ImageVector
    lateinit var alignment: Alignment
    val color: Color

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = Color.Transparent
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            icon = Icons.Outlined.ThumbUp
            alignment = Alignment.CenterStart
            color = Color.Transparent
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = Color.Transparent
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon, contentDescription = null
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                onComplete()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.Settled -> {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerAppBar(onFilterSelected: (FilterStatus) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.app_name),
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    expanded = true
                }
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Open Menu")
            }

            // menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // menu items
                DropdownMenuItem(
                    text = {
                        Text("All")
                    },
                    onClick = {
                        expanded = false
                        onFilterSelected(FilterStatus.ALL)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.all_filter),
                            contentDescription = "All"
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Completed")
                    },
                    onClick = {
                        expanded = false
                        onFilterSelected(FilterStatus.COMPLETED)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.completed_filter),
                            contentDescription = "Completed"
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Pending")
                    },
                    onClick = {
                        expanded = false
                        onFilterSelected(FilterStatus.PENDING)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.pending_filter),
                            contentDescription = "Pending"
                        )
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun EmptyTasksPlaceHolder(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_task_lens))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TTaskItemPreview() {
    TaskItem(taskUiState = TaskUiState(
        title = "Cooking Biriyani",
        description = "This application is designed for super shops. By using this application they can enlist all their products in one place and can deliver. Customers will get a one-stop solution for their daily shopping.",
        priority = Priority.HIGH,
        dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        status = Status.PENDING,
        dueDateFormatted = "Monday, Jan 15, 2024",
        priorityFormatted = "High",
        statusFormatted = "Pending",
        id = 1,
        showDescription = true,
        priorityColor = Color(0xfff99600)
    ))
}