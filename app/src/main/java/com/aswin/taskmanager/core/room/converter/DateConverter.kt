package com.aswin.taskmanager.core.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import timber.log.Timber

class DateConverter {
    private val formatter = LocalDate.Format { dayOfMonth(); char('-'); monthNumber(); char('-'); year() }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.format(format = formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let {
            try {
                LocalDate.parse(input = it, format = formatter)
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }
    }
}