package com.aswin.taskmanager.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aswin.taskmanager.core.room.converter.DateConverter
import com.aswin.taskmanager.core.room.entity.Task
import com.aswin.taskmanager.core.room.dao.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class) // Create a DateConverter class
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
