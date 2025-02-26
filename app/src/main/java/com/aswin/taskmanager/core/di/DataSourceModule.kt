package com.aswin.taskmanager.core.di

import android.app.Application
import androidx.room.Room
import com.aswin.taskmanager.core.room.dao.TaskDao
import com.aswin.taskmanager.core.room.TaskDatabase
import com.aswin.taskmanager.feature.create.data.dataSource.TaskLocalDataSource
import com.aswin.taskmanager.feature.create.data.dataSource.TaskLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(taskDao: TaskDao): TaskLocalDataSource {
        return TaskLocalDataSourceImpl(taskDao = taskDao)
    }
}