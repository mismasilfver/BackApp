package com.backpain.exercise.data.database.entity

import org.junit.Test
import org.junit.Assert.*

class ExerciseSetEntityTest {

    @Test
    fun `should create exercise set entity with all properties`() {
        // Given
        val exerciseSetEntity = ExerciseSetEntity(
            id = "beginner_set",
            name = "Beginner Set",
            description = "Gentle movements for pain relief",
            iconResourceId = 2131165187,
            exerciseIds = listOf("child_pose_001", "cat_pose_001", "pelvic_tilt_001", "gluteal_stretch_001"),
            estimatedDurationMinutes = 15
        )

        // Then
        assertEquals("beginner_set", exerciseSetEntity.id)
        assertEquals("Beginner Set", exerciseSetEntity.name)
        assertEquals("Gentle movements for pain relief", exerciseSetEntity.description)
        assertEquals(2131165187, exerciseSetEntity.iconResourceId)
        assertEquals(4, exerciseSetEntity.exerciseIds.size)
        assertEquals("child_pose_001", exerciseSetEntity.exerciseIds[0])
        assertEquals(15, exerciseSetEntity.estimatedDurationMinutes)
    }

    @Test
    fun `should handle empty exercise set`() {
        // Given
        val emptyExerciseSetEntity = ExerciseSetEntity(
            id = "empty_set",
            name = "Empty Set",
            description = "No exercises",
            iconResourceId = 2131165189,
            exerciseIds = emptyList(),
            estimatedDurationMinutes = 0
        )

        // Then
        assertTrue(emptyExerciseSetEntity.exerciseIds.isEmpty())
        assertEquals(0, emptyExerciseSetEntity.estimatedDurationMinutes)
        assertEquals("Empty Set", emptyExerciseSetEntity.name)
    }

    @Test
    fun `should store exercise IDs as list of strings`() {
        // Given
        val exerciseSetEntity = ExerciseSetEntity(
            id = "core_strength_set",
            name = "Core Strength",
            description = "Building support muscles",
            iconResourceId = 2131165188,
            exerciseIds = listOf("plank_001", "side_plank_001", "bridge_001", "bird_dog_001"),
            estimatedDurationMinutes = 20
        )

        // Then
        assertEquals(4, exerciseSetEntity.exerciseIds.size)
        assertTrue(exerciseSetEntity.exerciseIds.contains("plank_001"))
        assertTrue(exerciseSetEntity.exerciseIds.contains("bridge_001"))
        assertFalse(exerciseSetEntity.exerciseIds.contains("child_pose_001"))
    }

    @Test
    fun `should support different exercise set configurations`() {
        // Given - Flexibility set with overlapping exercises
        val flexibilitySet = ExerciseSetEntity(
            id = "flexibility_set",
            name = "Flexibility",
            description = "Improving range of motion",
            iconResourceId = 2131165190,
            exerciseIds = listOf("back_arch_001", "extension_001", "table_pose_001", "child_pose_001"),
            estimatedDurationMinutes = 18
        )

        // Then
        assertEquals("flexibility_set", flexibilitySet.id)
        assertEquals(4, flexibilitySet.exerciseIds.size)
        assertEquals(18, flexibilitySet.estimatedDurationMinutes)
        // Note: child_pose_001 appears in both beginner and flexibility sets
        assertTrue(flexibilitySet.exerciseIds.contains("child_pose_001"))
    }
}
