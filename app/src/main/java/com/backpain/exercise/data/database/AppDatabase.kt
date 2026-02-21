package com.backpain.exercise.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.backpain.exercise.data.database.dao.ExerciseDao
import com.backpain.exercise.data.database.dao.ExerciseSetDao
import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.data.database.entity.ExerciseSetEntity

@Database(
    entities = [
        ExerciseEntity::class,
        ExerciseSetEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseSetDao(): ExerciseSetDao
}
