package com.backpain.exercise.data.mapper

import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.domain.model.DifficultyLevel
import com.backpain.exercise.domain.model.Exercise
import org.junit.Test
import org.junit.Assert.*

class ExerciseMapperTest {

    @Test
    fun `should map ExerciseEntity to Exercise domain model`() {
        // Given
        val exerciseEntity = ExerciseEntity(
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
        val exercise = exerciseEntity.toDomainModel()

        // Then
        assertEquals("plank_001", exercise.id)
        assertEquals("Plank", exercise.title)
        assertEquals("Core strengthening exercise", exercise.description)
        assertEquals(2131165184, exercise.illustrationResourceId)
        assertEquals("dQw4w9WgXcQ", exercise.youtubeVideoId)
        assertEquals(30, exercise.durationSeconds)
        assertEquals(DifficultyLevel.BEGINNER, exercise.difficultyLevel)
        assertEquals(2, exercise.instructions.size)
        assertEquals("Start in push-up position", exercise.instructions[0])
        assertFalse(exercise.isCompleted)
    }

    @Test
    fun `should map Exercise domain model to ExerciseEntity`() {
        // Given
        val exercise = Exercise(
            id = "bridge_001",
            title = "Bridge",
            description = "Glute and lower back strengthening",
            illustrationResourceId = 2131165186,
            youtubeVideoId = null,
            durationSeconds = 60,
            difficultyLevel = DifficultyLevel.INTERMEDIATE,
            instructions = listOf("Lie on back", "Bend knees", "Lift hips"),
            isCompleted = true
        )

        // When
        val exerciseEntity = exercise.toEntity()

        // Then
        assertEquals("bridge_001", exerciseEntity.id)
        assertEquals("Bridge", exerciseEntity.title)
        assertEquals("Glute and lower back strengthening", exerciseEntity.description)
        assertEquals(2131165186, exerciseEntity.illustrationResourceId)
        assertNull(exerciseEntity.youtubeVideoId)
        assertEquals(60, exerciseEntity.durationSeconds)
        assertEquals("INTERMEDIATE", exerciseEntity.difficultyLevel)
        assertEquals(3, exerciseEntity.instructions.size)
        assertEquals("Lie on back", exerciseEntity.instructions[0])
        assertTrue(exerciseEntity.isCompleted)
    }

    @Test
    fun `should handle all difficulty levels correctly`() {
        // Given
        val beginnerExercise = Exercise(
            id = "beginner", title = "Beginner", description = "Desc",
            illustrationResourceId = 1, youtubeVideoId = null,
            durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = emptyList(), isCompleted = false
        )

        val intermediateExercise = Exercise(
            id = "intermediate", title = "Intermediate", description = "Desc",
            illustrationResourceId = 2, youtubeVideoId = null,
            durationSeconds = 45, difficultyLevel = DifficultyLevel.INTERMEDIATE,
            instructions = emptyList(), isCompleted = false
        )

        val advancedExercise = Exercise(
            id = "advanced", title = "Advanced", description = "Desc",
            illustrationResourceId = 3, youtubeVideoId = null,
            durationSeconds = 60, difficultyLevel = DifficultyLevel.ADVANCED,
            instructions = emptyList(), isCompleted = false
        )

        // When
        val beginnerEntity = beginnerExercise.toEntity()
        val intermediateEntity = intermediateExercise.toEntity()
        val advancedEntity = advancedExercise.toEntity()

        // Then
        assertEquals("BEGINNER", beginnerEntity.difficultyLevel)
        assertEquals("INTERMEDIATE", intermediateEntity.difficultyLevel)
        assertEquals("ADVANCED", advancedEntity.difficultyLevel)
    }

    @Test
    fun `should handle null YouTube video ID`() {
        // Given
        val exercise = Exercise(
            id = "child_pose_001", title = "Child Pose", description = "Gentle stretch",
            illustrationResourceId = 1, youtubeVideoId = null,
            durationSeconds = 45, difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = listOf("Kneel on floor"), isCompleted = false
        )

        // When
        val entity = exercise.toEntity()

        // Then
        assertNull(entity.youtubeVideoId)
    }

    @Test
    fun `should handle empty instructions list`() {
        // Given
        val exercise = Exercise(
            id = "simple_001", title = "Simple", description = "Simple exercise",
            illustrationResourceId = 1, youtubeVideoId = null,
            durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = emptyList(), isCompleted = false
        )

        // When
        val entity = exercise.toEntity()

        // Then
        assertTrue(entity.instructions.isEmpty())
    }
}
