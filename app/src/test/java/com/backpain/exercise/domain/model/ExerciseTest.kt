package com.backpain.exercise.domain.model

import org.junit.Test
import org.junit.Assert.*

class ExerciseTest {

    @Test
    fun `should create exercise with required properties`() {
        // Given
        val exercise = Exercise(
            id = "plank_001",
            title = "Plank",
            description = "Core strengthening exercise",
            illustrationResourceId = 2131165184, // R.drawable.ic_plank
            youtubeVideoId = "dQw4w9WgXcQ",
            durationSeconds = 30,
            difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = listOf(
                "Start in push-up position",
                "Hold your body straight",
                "Engage core muscles"
            ),
            isCompleted = false
        )

        // Then
        assertEquals("plank_001", exercise.id)
        assertEquals("Plank", exercise.title)
        assertEquals("Core strengthening exercise", exercise.description)
        assertEquals(2131165184, exercise.illustrationResourceId)
        assertEquals("dQw4w9WgXcQ", exercise.youtubeVideoId)
        assertEquals(30, exercise.durationSeconds)
        assertEquals(DifficultyLevel.BEGINNER, exercise.difficultyLevel)
        assertEquals(3, exercise.instructions.size)
        assertEquals("Start in push-up position", exercise.instructions[0])
        assertFalse(exercise.isCompleted)
    }

    @Test
    fun `should create exercise without YouTube video`() {
        // Given
        val exercise = Exercise(
            id = "child_pose_001",
            title = "Child Pose",
            description = "Gentle stretch and relaxation",
            illustrationResourceId = 2131165185,
            youtubeVideoId = null,
            durationSeconds = 45,
            difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = listOf("Kneel on floor", "Sit back on heels", "Rest forehead on ground"),
            isCompleted = false
        )

        // Then
        assertNull(exercise.youtubeVideoId)
        assertEquals("Child Pose", exercise.title)
    }

    @Test
    fun `should mark exercise as completed`() {
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
            isCompleted = false
        )

        // When
        val completedExercise = exercise.copy(isCompleted = true)

        // Then
        assertTrue(completedExercise.isCompleted)
        assertEquals(exercise.id, completedExercise.id)
        assertEquals(exercise.title, completedExercise.title)
    }

    @Test
    fun `should handle all difficulty levels`() {
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

        assertEquals(DifficultyLevel.BEGINNER, beginnerExercise.difficultyLevel)
        assertEquals(DifficultyLevel.INTERMEDIATE, intermediateExercise.difficultyLevel)
        assertEquals(DifficultyLevel.ADVANCED, advancedExercise.difficultyLevel)
    }
}
