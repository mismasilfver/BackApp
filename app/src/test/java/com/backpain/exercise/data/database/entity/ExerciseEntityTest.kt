package com.backpain.exercise.data.database.entity

import org.junit.Test
import org.junit.Assert.*

class ExerciseEntityTest {

    @Test
    fun `should create exercise entity with all properties`() {
        // Given
        val exerciseEntity = ExerciseEntity(
            id = "plank_001",
            title = "Plank",
            description = "Core strengthening exercise",
            illustrationResourceId = 2131165184,
            youtubeVideoId = "dQw4w9WgXcQ",
            durationSeconds = 30,
            difficultyLevel = "BEGINNER",
            instructions = listOf(
                "Start in push-up position",
                "Hold your body straight",
                "Engage core muscles"
            ),
            isCompleted = false
        )

        // Then
        assertEquals("plank_001", exerciseEntity.id)
        assertEquals("Plank", exerciseEntity.title)
        assertEquals("Core strengthening exercise", exerciseEntity.description)
        assertEquals(2131165184, exerciseEntity.illustrationResourceId)
        assertEquals("dQw4w9WgXcQ", exerciseEntity.youtubeVideoId)
        assertEquals(30, exerciseEntity.durationSeconds)
        assertEquals("BEGINNER", exerciseEntity.difficultyLevel)
        assertEquals(3, exerciseEntity.instructions.size)
        assertEquals("Start in push-up position", exerciseEntity.instructions[0])
        assertFalse(exerciseEntity.isCompleted)
    }

    @Test
    fun `should create exercise entity without YouTube video`() {
        // Given
        val exerciseEntity = ExerciseEntity(
            id = "child_pose_001",
            title = "Child Pose",
            description = "Gentle stretch and relaxation",
            illustrationResourceId = 2131165185,
            youtubeVideoId = null,
            durationSeconds = 45,
            difficultyLevel = "BEGINNER",
            instructions = listOf("Kneel on floor", "Sit back on heels", "Rest forehead on ground"),
            isCompleted = false
        )

        // Then
        assertNull(exerciseEntity.youtubeVideoId)
        assertEquals("Child Pose", exerciseEntity.title)
        assertEquals("BEGINNER", exerciseEntity.difficultyLevel)
    }

    @Test
    fun `should support all difficulty levels as strings`() {
        // Given
        val beginnerExercise = ExerciseEntity(
            id = "beginner", title = "Beginner", description = "Desc",
            illustrationResourceId = 1, youtubeVideoId = null,
            durationSeconds = 30, difficultyLevel = "BEGINNER",
            instructions = emptyList(), isCompleted = false
        )

        val intermediateExercise = ExerciseEntity(
            id = "intermediate", title = "Intermediate", description = "Desc",
            illustrationResourceId = 2, youtubeVideoId = null,
            durationSeconds = 45, difficultyLevel = "INTERMEDIATE",
            instructions = emptyList(), isCompleted = false
        )

        val advancedExercise = ExerciseEntity(
            id = "advanced", title = "Advanced", description = "Desc",
            illustrationResourceId = 3, youtubeVideoId = null,
            durationSeconds = 60, difficultyLevel = "ADVANCED",
            instructions = emptyList(), isCompleted = false
        )

        // Then
        assertEquals("BEGINNER", beginnerExercise.difficultyLevel)
        assertEquals("INTERMEDIATE", intermediateExercise.difficultyLevel)
        assertEquals("ADVANCED", advancedExercise.difficultyLevel)
    }

    @Test
    fun `should update completion status`() {
        // Given
        val exerciseEntity = ExerciseEntity(
            id = "bridge_001",
            title = "Bridge",
            description = "Glute and lower back strengthening",
            illustrationResourceId = 2131165186,
            youtubeVideoId = null,
            durationSeconds = 60,
            difficultyLevel = "INTERMEDIATE",
            instructions = listOf("Lie on back", "Bend knees", "Lift hips"),
            isCompleted = false
        )

        // When
        val completedExercise = exerciseEntity.copy(isCompleted = true)

        // Then
        assertTrue(completedExercise.isCompleted)
        assertEquals(exerciseEntity.id, completedExercise.id)
        assertEquals(exerciseEntity.title, completedExercise.title)
    }
}
