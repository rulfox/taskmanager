package com.aswin.taskmanager.core.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

object ComposeUtils {
    @Composable
    fun isPortrait(): Boolean {
        val configuration = LocalConfiguration.current
        return remember(configuration) { configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
    }
}