package com.aswin.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aswin.taskmanager.core.navigation.Screen
import com.aswin.taskmanager.feature.create.presentation.TaskCreationScreen
import com.aswin.taskmanager.feature.details.presentation.TaskDetailScreen
import com.aswin.taskmanager.feature.list.presentation.TaskListScreen
import com.aswin.taskmanager.feature.settings.presentation.SettingsScreen
import com.aswin.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TaskManagerTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Task Manager") },
                            navigationIcon = {
                                if(navController.previousBackStackEntry != null){
                                    IconButton(onClick = {
                                        navController.navigateUp()
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ){ innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.TaskListScreen
                    ) {
                        composable<Screen.TaskListScreen> {
                            TaskListScreen()
                        }
                        composable<Screen.TaskCreationScreen> {
                            TaskCreationScreen()
                        }
                        composable<Screen.TaskDetailScreen> {
                            TaskDetailScreen()
                        }
                        composable<Screen.SettingsScreen> {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagerTheme {
        Greeting("Android")
    }
}