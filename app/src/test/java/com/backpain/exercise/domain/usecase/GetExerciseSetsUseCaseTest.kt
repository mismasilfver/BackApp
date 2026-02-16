package com.backpain.exercise.domain.usecase

import com.backpain.exercise.domain.model.ExerciseSet
import com.backpain.exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetExerciseSetsUseCaseTest {

    @Mock
    private lateinit var repository: ExerciseRepository

    private lateinit var getExerciseSetsUseCase: GetExerciseSetsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getExerciseSetsUseCase = GetExerciseSetsUseCase(repository)
    }

    @Test
    fun `should return all exercise sets from repository`() = runTest {
        // Given
        val expectedExerciseSets = listOf(
            ExerciseSet(
                id = "beginner_set",
                name = "Beginner Set",
                description = "Gentle movements for pain relief",
                iconResourceId = 2131165187,
                exerciseIds = listOf("child_pose_001", "cat_pose_001"),
                estimatedDurationMinutes = 15
            ),
            ExerciseSet(
                id = "core_strength_set",
                name = "Core Strength",
                description = "Building support muscles",
                iconResourceId = 2131165188,
                exerciseIds = listOf("plank_001", "bridge_001"),
                estimatedDurationMinutes = 20
            )
        )

        whenever(repository.getExerciseSets()).thenReturn(flowOf(expectedExerciseSets))

        // When
        val result = getExerciseSetsUseCase().toList().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("beginner_set", result[0].id)
        assertEquals("core_strength_set", result[1].id)
    }

    @Test
    fun `should return empty list when repository has no exercise sets`() = runTest {
        // Given
        whenever(repository.getExerciseSets()).thenReturn(flowOf(emptyList()))

        // When
        val result = getExerciseSetsUseCase().toList().first()

        // Then
        assertTrue(result.isEmpty())
    }
}
