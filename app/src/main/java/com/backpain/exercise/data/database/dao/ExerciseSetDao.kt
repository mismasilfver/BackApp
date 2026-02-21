package com.backpain.exercise.data.database.dao

import androidx.room.*
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseSetDao {
    
    @Query("SELECT * FROM exercise_sets ORDER BY name")
    fun getAllExerciseSets(): Flow<List<ExerciseSetEntity>>
    
    @Query("SELECT * FROM exercise_sets WHERE id = :id")
    fun getExerciseSetById(id: String): Flow<ExerciseSetEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseSet(exerciseSet: ExerciseSetEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseSets(exerciseSets: List<ExerciseSetEntity>)
    
    @Query("DELETE FROM exercise_sets")
    suspend fun clearAllExerciseSets()
}
