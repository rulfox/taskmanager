package com.aswin.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aswin.taskmanager.core.navigation.Screen
import com.aswin.taskmanager.feature.create.presentation.TaskCreationScreen
import com.aswin.taskmanager.feature.details.presentation.TaskDetailScreen
import com.aswin.taskmanager.feature.list.presentation.TaskListScreen
import com.aswin.taskmanager.feature.settings.presentation.SettingsScreen
import com.aswin.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TaskManagerTheme(
                darkTheme = false,
                dynamicColor = false
            ){
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Screen.TaskListScreen
                ) {
                    composable<Screen.TaskListScreen> {
                        TaskListScreen(onCreateTask = {
                            navController.navigate(Screen.TaskCreationScreen)
                        })
                    }
                    composable<Screen.TaskCreationScreen> {
                        TaskCreationScreen(onBackPressed = {
                            navController.navigateUp()
                        })
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