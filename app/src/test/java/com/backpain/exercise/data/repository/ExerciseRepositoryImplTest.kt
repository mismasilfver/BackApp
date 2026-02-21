package com.backpain.exercise.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backpain.exercise.data.database.AppDatabase
import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import com.backpain.exercise.data.mapper.toDomainModel
import com.backpain.exercise.data.mapper.toEntity
import com.backpain.exercise.domain.model.DifficultyLevel
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.model.ExerciseSet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExerciseRepositoryImplTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: ExerciseRepositoryImpl

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = ExerciseRepositoryImpl(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `should get all exercise sets`() = runTest {
        // Given
        val exerciseSets = listOf(
            ExerciseSetEntity(
                id = "beginner_set", name = "Beginner Set", description = "Gentle movements",
                iconResourceId = 1, exerciseIds = listOf("child_pose_001"), estimatedDurationMinutes = 15
            ),
            ExerciseSetEntity(
                id = "core_strength_set", name = "Core Strength", description = "Building support",
                iconResourceId = 2, exerciseIds = listOf("plank_001"), estimatedDurationMinutes = 20
            )
        )
        database.exerciseSetDao().insertExerciseSets(exerciseSets)

        // When
        val result = repository.getExerciseSets().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Beginner Set", result[0].name)
        assertEquals("Core Strength", result[1].name)
    }

    @Test
    fun `should get exercises by set ID`() = runTest {
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
        database.exerciseDao().insertExercises(exercises)

        val exerciseSet = ExerciseSetEntity(
            id = "beginner_set", name = "Beginner Set", description = "Gentle movements",
            iconResourceId = 1, exerciseIds = listOf("child_pose_001", "cat_pose_001"), estimatedDurationMinutes = 15
        )
        database.exerciseSetDao().insertExerciseSet(exerciseSet)

        // When
        val result = repository.getExercisesBySet("beginner_set").first()

        // Then
        assertEquals(2, result.size)
        assertTrue(result.any { it.id == "child_pose_001" })
        assertTrue(result.any { it.id == "cat_pose_001" })
        assertFalse(result.any { it.id == "plank_001" })
    }

    @Test
    fun `should get exercise by ID`() = runTest {
        // Given
        val exercise = ExerciseEntity(
            id = "plank_001", title = "Plank", description = "Core strengthening",
            illustrationResourceId = 1, youtubeVideoId = "dQw4w9WgXcQ", durationSeconds = 30,
            difficultyLevel = "BEGINNER", instructions = listOf("Start in push-up position"), isCompleted = false
        )
        database.exerciseDao().insertExercise(exercise)

        // When
        val result = repository.getExerciseById("plank_001").first()

        // Then
        assertNotNull(result)
        assertEquals("plank_001", result?.id)
        assertEquals("Plank", result?.title)
        assertEquals("dQw4w9WgXcQ", result?.youtubeVideoId)
        assertEquals(DifficultyLevel.BEGINNER, result?.difficultyLevel)
        assertFalse(result?.isCompleted ?: true)
    }

    @Test
    fun `should mark exercise as completed`() = runTest {
        // Given
        val exercise = ExerciseEntity(
            id = "bridge_001", title = "Bridge", description = "Glute exercise",
            illustrationResourceId = 1, youtubeVideoId = null, durationSeconds = 45,
            difficultyLevel = "INTERMEDIATE", instructions = emptyList(), isCompleted = false
        )
        database.exerciseDao().insertExercise(exercise)

        // When
        repository.markExerciseCompleted("bridge_001")
        val result = repository.getExerciseById("bridge_001").first()

        // Then
        assertNotNull(result)
        assertTrue(result?.isCompleted ?: false)
    }

    @Test
    fun `should reset exercise completion`() = runTest {
        // Given
        val exercise = ExerciseEntity(
            id = "bridge_001", title = "Bridge", description = "Glute exercise",
            illustrationResourceId = 1, youtubeVideoId = null, durationSeconds = 45,
            difficultyLevel = "INTERMEDIATE", instructions = emptyList(), isCompleted = true
        )
        database.exerciseDao().insertExercise(exercise)

        // When
        repository.resetExerciseCompletion("bridge_001")
        val result = repository.getExerciseById("bridge_001").first()

        // Then
        assertNotNull(result)
        assertFalse(result?.isCompleted ?: true)
    }

    @Test
    fun `should return null for non-existent exercise`() = runTest {
        // When
        val result = repository.getExerciseById("non_existent").first()

        // Then
        assertNull(result)
    }

    @Test
    fun `should handle empty exercise sets list`() = runTest {
        // When
        val result = repository.getExerciseSets().first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should handle exercises by non-existent set`() = runTest {
        // When
        val result = repository.getExercisesBySet("non_existent_set").first()

        // Then
        assertTrue(result.isEmpty())
    }
}
