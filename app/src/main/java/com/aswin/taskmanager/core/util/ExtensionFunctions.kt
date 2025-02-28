package com.aswin.taskmanager.core.util

import android.content.Context
import android.widget.Toast
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun LocalDate.formatDate(pattern: String = "d MMMM, yyyy"): String{
    return this.toJavaLocalDate().format(DateTimeFormatter.ofPattern(pattern))
}