package com.aswin.taskmanager.feature.create.presentation

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aswin.taskmanager.R
import com.aswin.taskmanager.feature.create.data.model.PriorityState
import com.aswin.taskmanager.ui.theme.TaskManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskAppbar(onBackPressed: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f).clickable {
                        onBackPressed()
                    },
                    text = stringResource(R.string.create_task),
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.alpha(0f),
                onClick = {
                    expanded = true
                }
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Open Menu")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun PriorityDropDown(
    modifier: Modifier,
    priorities: List<PriorityState> = listOf(PriorityState.LOW, PriorityState.MEDIUM, PriorityState.HIGH),
    selectedPriorityState: PriorityState,
    onPriorityStateChanged: (PriorityState) -> Unit
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 15.dp))
                    .clickable(enabled = true, onClick = { isDropDownExpanded.value = true }),
            ) {
                PriorityDropDownItem(
                    priorityState = selectedPriorityState
                )
            }
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                priorities.forEachIndexed { index, priorityState ->
                    DropdownMenuItem(
                        modifier = Modifier.background(Color.Transparent),
                        text = {
                            PriorityDropDownItem(
                                priorityState = priorityState
                            )
                        },
                        onClick = {
                            isDropDownExpanded.value = false
                            onPriorityStateChanged(priorityState)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityDropDownItem(priorityState: PriorityState){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
    ) {
        Image(
            modifier = Modifier.padding(top = 4.dp),
            painter = painterResource(id = R.drawable.priority_high),
            contentDescription = "Task Icon"
        )
        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Priority",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                text = priorityState.label,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .requiredWidth(width = 10.dp)
                .requiredHeight(height = 25.dp)
                .clip(shape = RoundedCornerShape(22.dp))
                .background(color = priorityState.color)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownDemoPreview() {
    TaskManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PriorityDropDown(
                modifier = Modifier.fillMaxWidth(),
                priorities = listOf(PriorityState.LOW, PriorityState.MEDIUM, PriorityState.HIGH),
                selectedPriorityState = PriorityState.HIGH,
                onPriorityStateChanged = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTaskAppbarPreview() {
    TaskManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CreateTaskAppbar(
                    onBackPressed = {}
                )
            }
        }
    }
}
