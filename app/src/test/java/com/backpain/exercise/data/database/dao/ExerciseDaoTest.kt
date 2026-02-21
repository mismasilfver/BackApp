package com.backpain.exercise.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backpain.exercise.data.database.AppDatabase
import com.backpain.exercise.data.database.entity.ExerciseEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var exerciseDao: ExerciseDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        exerciseDao = database.exerciseDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `should insert and retrieve exercise`() = runTest {
        // Given
        val exercise = ExerciseEntity(
            id = "plank_001",
            title = "Plank",
            description = "Core strengthening exercise",
            illustrationResourceId = 2131165184,
            youtubeVideoId = "dQw4w9WgXcQ",
            durationSeconds = 30,
            difficultyLevel = "BEGINNER",
            instructions = listOf("Start in push-up position", "Hold body straight"),
            isCompleted = false
        )

        // When
        exerciseDao.insertExercise(exercise)
        val retrieved = exerciseDao.getExerciseById("plank_001").first()

        // Then
        assertNotNull(retrieved)
        assertEquals("plank_001", retrieved?.id)
        assertEquals("Plank", retrieved?.title)
        assertEquals("BEGINNER", retrieved?.difficultyLevel)
        assertFalse(retrieved?.isCompleted ?: true)
    }

    @Test
    fun `should get all exercises`() = runTest {
        // Given
        val exercises = listOf(
            ExerciseEntity(
                id = "plank_001", title = "Plank", description = "Core exercise",
                illustrationResourceId = 1, youtubeVideoId = null, durationSeconds = 30,
                difficultyLevel = "BEGINNER", instructions = emptyList(), isCompleted = false
            ),
            ExerciseEntity(
                id = "bridge_001", title = "Bridge", description = "Glute exercise",
                illustrationResourceId = 2, youtubeVideoId = null, durationSeconds = 45,
                difficultyLevel = "INTERMEDIATE", instructions = emptyList(), isCompleted = false
            )
        )

        // When
        exercises.forEach { exerciseDao.insertExercise(it) }
        val allExercises = exerciseDao.getAllExercises().first()

        // Then
        assertEquals(2, allExercises.size)
        assertTrue(allExercises.any { it.id == "plank_001" })
        assertTrue(allExercises.any { it.id == "bridge_001" })
    }

    @Test
    fun `should get exercises by set IDs`() = runTest {
        // Given
        val exercises = listOf(
            ExerciseEntity(
                id = "child_pose_001", title = "Child Pose", description = "Gentle stretch",
                illustrationResourceId = 1, youtubeVideoId = null, durationSeconds = 30,
                difficultyLevel = "BEGINNER", instructions = emptyList(), isCompleted = false
            ),
            ExerciseEntity(
                id = "cat_pose_001", title = "Cat Pose", description = "Spinal flexibility",
                illustrationResourceId = 2, youtubeVideoId = null, durationSeconds = 30,
                difficultyLevel = "BEGINNER", instructions = emptyList(), isCompleted = false
            ),
            ExerciseEntity(
                id = "plank_001", title = "Plank", description = "Core strength",
                illustrationResourceId = 3, youtubeVideoId = null, durationSeconds = 30,
                difficultyLevel = "BEGINNER", instructions = emptyList(), isCompleted = false
            )
        )
        exercises.forEach { exerciseDao.insertExercise(it) }

        // When
        val beginnerExercises = exerciseDao.getExercisesBySetIds(
            listOf("child_pose_001", "cat_pose_001")
        ).first()

        // Then
        assertEquals(2, beginnerExercises.size)
        assertTrue(beginnerExercises.any { it.id == "child_pose_001" })
        assertTrue(beginnerExercises.any { it.id == "cat_pose_001" })
        assertFalse(beginnerExercises.any { it.id == "plank_001" })
    }

    @Test
    fun `should update exercise completion status`() = runTest {
        // Given
        val exercise = ExerciseEntity(
            id = "bridge_001", title = "Bridge", description = "Glute exercise",
            illustrationResourceId = 1, youtubeVideoId = null, durationSeconds = 45,
            difficultyLevel = "INTERMEDIATE", instructions = emptyList(), isCompleted = false
        )
        exerciseDao.insertExercise(exercise)

        // When
        exerciseDao.updateExerciseCompletion("bridge_001", true)
        val updated = exerciseDao.getExerciseById("bridge_001").first()

        // Then
        assertNotNull(updated)
        assertTrue(updated?.isCompleted ?: false)
    }

    @Test
    fun `should return null for non-existent exercise`() = runTest {
        // When
        val result = exerciseDao.getExerciseById("non_existent").first()

        // Then
        assertNull(result)
    }
}
