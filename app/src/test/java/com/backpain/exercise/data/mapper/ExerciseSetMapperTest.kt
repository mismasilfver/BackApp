package com.backpain.exercise.data.mapper

import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import com.backpain.exercise.domain.model.ExerciseSet
import org.junit.Test
import org.junit.Assert.*

class ExerciseSetMapperTest {

    @Test
    fun `should map ExerciseSetEntity to ExerciseSet domain model`() {
        // Given
        val exerciseSetEntity = ExerciseSetEntity(
            id = "beginner_set",
            name = "Beginner Set",
            description = "Gentle movements for pain relief",
            iconResourceId = 2131165187,
            exerciseIds = listOf("child_pose_001", "cat_pose_001", "pelvic_tilt_001", "gluteal_stretch_001"),
            estimatedDurationMinutes = 15
        )

        // When
        val exerciseSet = exerciseSetEntity.toDomainModel()

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
    fun `should map ExerciseSet domain model to ExerciseSetEntity`() {
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
        val exerciseSetEntity = exerciseSet.toEntity()

        // Then
        assertEquals("core_strength_set", exerciseSetEntity.id)
        assertEquals("Core Strength", exerciseSetEntity.name)
        assertEquals("Building support muscles", exerciseSetEntity.description)
        assertEquals(2131165188, exerciseSetEntity.iconResourceId)
        assertEquals(4, exerciseSetEntity.exerciseIds.size)
        assertEquals("plank_001", exerciseSetEntity.exerciseIds[0])
        assertEquals(20, exerciseSetEntity.estimatedDurationMinutes)
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

        // When
        val entity = emptyExerciseSet.toEntity()

        // Then
        assertEquals("empty_set", entity.id)
        assertTrue(entity.exerciseIds.isEmpty())
        assertEquals(0, entity.estimatedDurationMinutes)
    }

    @Test
    fun `should handle single exercise set`() {
        // Given
        val singleExerciseSet = ExerciseSet(
            id = "single_set",
            name = "Single Exercise",
            description = "Only one exercise",
            iconResourceId = 2131165190,
            exerciseIds = listOf("plank_001"),
            estimatedDurationMinutes = 5
        )

        // When
        val entity = singleExerciseSet.toEntity()

        // Then
        assertEquals(1, entity.exerciseIds.size)
        assertEquals("plank_001", entity.exerciseIds[0])
    }

    @Test
    fun `should support overlapping exercises in different sets`() {
        // Given - child_pose_001 appears in both sets
        val beginnerSet = ExerciseSet(
            id = "beginner_set", name = "Beginner Set", description = "Gentle movements",
            iconResourceId = 1, exerciseIds = listOf("child_pose_001", "cat_pose_001"), estimatedDurationMinutes = 15
        )

        val flexibilitySet = ExerciseSet(
            id = "flexibility_set", name = "Flexibility", description = "Range of motion",
            iconResourceId = 2, exerciseIds = listOf("back_arch_001", "child_pose_001"), estimatedDurationMinutes = 18
        )

        // When
        val beginnerEntity = beginnerSet.toEntity()
        val flexibilityEntity = flexibilitySet.toEntity()

        // Then
        assertTrue(beginnerEntity.exerciseIds.contains("child_pose_001"))
        assertTrue(flexibilityEntity.exerciseIds.contains("child_pose_001"))
        assertEquals(2, beginnerEntity.exerciseIds.size)
        assertEquals(2, flexibilityEntity.exerciseIds.size)
    }
}
