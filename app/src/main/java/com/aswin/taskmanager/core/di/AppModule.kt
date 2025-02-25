package com.aswin.taskmanager.core.di

import android.app.Application
import androidx.room.Room
import com.aswin.taskmanager.core.room.dao.TaskDao
import com.aswin.taskmanager.core.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(application, TaskDatabase::class.java, "task_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }
}
