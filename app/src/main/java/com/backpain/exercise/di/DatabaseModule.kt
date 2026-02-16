package com.backpain.exercise.di

import android.content.Context
import androidx.room.Room
import com.backpain.exercise.data.database.AppDatabase
import com.backpain.exercise.data.database.dao.ExerciseDao
import com.backpain.exercise.data.database.dao.ExerciseSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "back_pain_exercise_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao()
    }

    @Provides
    fun provideExerciseSetDao(database: AppDatabase): ExerciseSetDao {
        return database.exerciseSetDao()
    }
}
