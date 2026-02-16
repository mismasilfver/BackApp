package com.backpain.exercise.domain.model

import org.junit.Test
import org.junit.Assert.*

class ExerciseSetTest {

    @Test
    fun `should create exercise set with required properties`() {
        // Given
        val exerciseSet = ExerciseSet(
            id = "beginner_set",
            name = "Beginner Set",
            description = "Gentle movements for pain relief",
            iconResourceId = 2131165187, // R.drawable.ic_beginner
            exerciseIds = listOf("child_pose_001", "cat_pose_001", "pelvic_tilt_001", "gluteal_stretch_001"),
            estimatedDurationMinutes = 15
        )

        // Then
        assertEquals("beginner_set", exerciseSet.id)
        assertEquals("Beginner Set", exerciseSet.name)
        assertEquals("Gentle movements for pain relief", exerciseSet.description)
        assertEquals(2131165187, exerciseSet.iconResourceId)
        assertEquals(4, exerciseSet.exerciseIds.size)
        assertEquals("child_pose_001", exerciseSet.exerciseIds[0])
        assertEquals(15, exerciseSet.estimatedDurationMinutes)
    }

    @Test
    fun `should calculate completed exercise count`() {
        // Given
        val exerciseSet = ExerciseSet(
            id = "core_strength_set",
            name = "Core Strength",
            description = "Building support muscles",
            iconResourceId = 2131165188,
            exerciseIds = listOf("plank_001", "side_plank_001", "bridge_001", "bird_dog_001"),
            estimatedDurationMinutes = 20
        )

        // When
        val completedExercises = listOf("plank_001", "bridge_001")
        val completedCount = exerciseSet.exerciseIds.count { it in completedExercises }

        // Then
        assertEquals(2, completedCount)
        assertEquals(4, exerciseSet.exerciseIds.size)
    }

    @Test
    fun `should handle empty exercise set`() {
        // Given
        val emptyExerciseSet = ExerciseSet(
            id = "empty_set",
            name = "Empty Set",
            description = "No exercises",
            iconResourceId = 2131165189,
            exerciseIds = emptyList(),
            estimatedDurationMinutes = 0
        )

        // Then
        assertTrue(emptyExerciseSet.exerciseIds.isEmpty())
        assertEquals(0, emptyExerciseSet.estimatedDurationMinutes)
        assertEquals("Empty Set", emptyExerciseSet.name)
    }

    @Test
    fun `should track completion progress`() {
        // Given
        val exerciseSet = ExerciseSet(
            id = "flexibility_set",
            name = "Flexibility",
            description = "Improving range of motion",
            iconResourceId = 2131165190,
            exerciseIds = listOf("back_arch_001", "extension_001", "table_pose_001", "child_pose_001"),
            estimatedDurationMinutes = 18
        )

        // When
        val completedExercises = listOf("back_arch_001", "extension_001", "table_pose_001")
        val completionPercentage = (completedExercises.size.toDouble() / exerciseSet.exerciseIds.size) * 100

        // Then
        assertEquals(75.0, completionPercentage, 0.0)
        assertEquals(4, exerciseSet.exerciseIds.size)
    }
}
