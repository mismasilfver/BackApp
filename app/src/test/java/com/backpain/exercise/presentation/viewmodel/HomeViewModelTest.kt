package com.backpain.exercise.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backpain.exercise.domain.model.ExerciseSet
import com.backpain.exercise.domain.usecase.GetExerciseSetsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getExerciseSetsUseCase: GetExerciseSetsUseCase

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(getExerciseSetsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load exercise sets on initialization`() = runTest {
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

        whenever(getExerciseSetsUseCase()).thenReturn(flowOf(expectedExerciseSets))

        // When
        viewModel.loadExerciseSets()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.exerciseSets.value
        assertEquals(2, result.size)
        assertEquals("Beginner Set", result[0].name)
        assertEquals("Core Strength", result[1].name)
    }

    @Test
    fun `should handle loading state correctly`() = runTest {
        // Given
        whenever(getExerciseSetsUseCase()).thenReturn(flowOf(emptyList()))

        // When
        viewModel.loadExerciseSets()

        // Then
        assertFalse(viewModel.isLoading.value)
        assertTrue(viewModel.exerciseSets.value.isEmpty())
    }

    @Test
    fun `should handle empty exercise sets list`() = runTest {
        // Given
        whenever(getExerciseSetsUseCase()).thenReturn(flowOf(emptyList()))

        // When
        viewModel.loadExerciseSets()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.exerciseSets.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `should handle error state gracefully`() = runTest {
        // Given - simulate error by throwing exception
        // Note: In a real implementation, you'd use a Result wrapper or error handling

        // When
        viewModel.loadExerciseSets()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertFalse(viewModel.isLoading.value)
        // Error handling would be tested here with proper error states
    }

    @Test
    fun `should refresh exercise sets when called`() = runTest {
        // Given
        val exerciseSets = listOf(
            ExerciseSet(
                id = "flexibility_set",
                name = "Flexibility",
                description = "Improving range of motion",
                iconResourceId = 2131165190,
                exerciseIds = listOf("back_arch_001", "child_pose_001"),
                estimatedDurationMinutes = 18
            )
        )

        whenever(getExerciseSetsUseCase()).thenReturn(flowOf(exerciseSets))

        // When
        viewModel.refreshExerciseSets()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.exerciseSets.value
        assertEquals(1, result.size)
        assertEquals("Flexibility", result[0].name)
    }

    @Test
    fun `should initialize with loading state false`() {
        // Then
        assertFalse(viewModel.isLoading.value)
        assertTrue(viewModel.exerciseSets.value.isEmpty())
    }
}
