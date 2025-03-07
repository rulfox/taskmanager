package com.aswin.taskmanager.feature.list.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.launch
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
            Row(
                modifier = Modifier
                    .padding(top = 9.dp, end = 16.dp)
                    .align(alignment = Alignment.TopEnd)
            ) {
                if(taskUiState.isCompleted){
                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clip(shape = RoundedCornerShape(31.dp))
                            .background(color = Color(0xff00A707).copy(alpha = 0.1f))
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
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(31.dp))
                        .background(color = Color(0xfff0f0f0))
                ){
                    Text(
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                        text = taskUiState.dueDateFormatted,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)) {
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
                    if(taskUiState.showDescription){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            text = "Description",
                            color = Color(0xff6e6a7c),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
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
fun TaskManagerAppBar(onFilterRequested: () -> Unit) {
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
                    onFilterRequested()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter_bottomsheet),
                    contentDescription = "Open Menu",
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

@Composable
fun EmptyTasksImagePlaceHolder(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.empty_tasks),
            contentDescription = "No Task Created"
        )
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
            text = "No Task Created",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(selectedFilter: FilterStatus, onSelected: (FilterStatus) -> Unit, onDismissRequest: () -> Unit){
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 16.dp,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)

                )
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                    textAlign = TextAlign.Start,
                    text = "Apply Filter",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    FilterChip(label = "All", isSelected = selectedFilter == FilterStatus.ALL, color = Color(0xFF764AF1)){
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                onSelected(FilterStatus.ALL)
                            }
                        }
                    }
                    FilterChip(label = "Pending", isSelected = selectedFilter == FilterStatus.PENDING, color = Color(0xFFC19808)){
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                onSelected(FilterStatus.PENDING)
                            }
                        }
                    }
                    FilterChip(label = "Completed", isSelected = selectedFilter == FilterStatus.COMPLETED, color = Color(0xFF92BA92)){
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                onSelected(FilterStatus.COMPLETED)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun FilterBottomSheetPreview() {
    FilterBottomSheet(
        selectedFilter = FilterStatus.ALL,
        onSelected = {},
        onDismissRequest = {}
    )
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, color: Color, onClicked:() -> Unit){
    Box {
        if(isSelected){
            Box(
                modifier = Modifier
                    .requiredSize(size = 12.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xff764af1))
                    .align(Alignment.TopEnd)
            )
        } else {
            Box(
                modifier = Modifier
                    .requiredSize(size = 12.dp)
                    .background(color = Color.Transparent)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 4.dp, end = 4.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = color.copy(alpha = 0.2f))
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable { onClicked() }
        ) {
            Text(
                text = label,
                color = color,
                lineHeight = 9.29.em,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium))
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TaskItemPreview() {
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
        priorityColor = Color(0xfff99600),
        isCompleted = true,
        isDue = false
    ))
}