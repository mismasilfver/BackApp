package com.backpain.exercise.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.model.DifficultyLevel
import com.backpain.exercise.domain.usecase.GetExercisesBySetUseCase
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
class ExerciseSetDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getExercisesBySetUseCase: GetExercisesBySetUseCase

    private lateinit var viewModel: ExerciseSetDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load exercises for given set ID`() = runTest {
        // Given
        val setId = "beginner_set"
        val expectedExercises = listOf(
            Exercise(
                id = "child_pose_001",
                title = "Child Pose",
                description = "Gentle stretch and relaxation",
                illustrationResourceId = 2131165188,
                youtubeVideoId = "9kGLBpoIFQ8",
                durationSeconds = 60,
                difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = listOf("Kneel on floor", "Sit back on heels"),
                isCompleted = false
            ),
            Exercise(
                id = "cat_pose_001",
                title = "Cat Pose",
                description = "Spinal flexibility exercise",
                illustrationResourceId = 2131165190,
                youtubeVideoId = "YRQk6TmPjSc",
                durationSeconds = 45,
                difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = listOf("Start on all fours", "Arch your back"),
                isCompleted = true
            )
        )

        whenever(getExercisesBySetUseCase(setId)).thenReturn(flowOf(expectedExercises))

        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)
        viewModel.loadExercises(setId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val exercises = viewModel.exercises.value
        assertEquals(2, exercises.size)
        assertEquals("Child Pose", exercises[0].title)
        assertEquals("Cat Pose", exercises[1].title)
        assertEquals(1, exercises.count { it.isCompleted })
    }

    @Test
    fun `should handle loading state correctly`() = runTest {
        // Given
        val setId = "core_strength_set"
        whenever(getExercisesBySetUseCase(setId)).thenReturn(flowOf(emptyList()))

        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)
        viewModel.loadExercises(setId)

        // Then
        assertFalse(viewModel.isLoading.value)
        assertTrue(viewModel.exercises.value.isEmpty())
    }

    @Test
    fun `should handle empty exercises list`() = runTest {
        // Given
        val setId = "empty_set"
        whenever(getExercisesBySetUseCase(setId)).thenReturn(flowOf(emptyList()))

        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)
        viewModel.loadExercises(setId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.exercises.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `should calculate completion progress`() = runTest {
        // Given
        val setId = "beginner_set"
        val exercises = listOf(
            Exercise(
                id = "exercise1", title = "Exercise 1", description = "Desc",
                illustrationResourceId = 1, youtubeVideoId = null,
                durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = emptyList(), isCompleted = true
            ),
            Exercise(
                id = "exercise2", title = "Exercise 2", description = "Desc",
                illustrationResourceId = 2, youtubeVideoId = null,
                durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = emptyList(), isCompleted = false
            ),
            Exercise(
                id = "exercise3", title = "Exercise 3", description = "Desc",
                illustrationResourceId = 3, youtubeVideoId = null,
                durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = emptyList(), isCompleted = true
            )
        )

        whenever(getExercisesBySetUseCase(setId)).thenReturn(flowOf(exercises))

        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)
        viewModel.loadExercises(setId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val progress = viewModel.completionProgress.value
        assertEquals(66.67, progress, 0.01) // 2 out of 3 completed
    }

    @Test
    fun `should handle zero completion progress`() = runTest {
        // Given
        val setId = "new_set"
        val exercises = listOf(
            Exercise(
                id = "exercise1", title = "Exercise 1", description = "Desc",
                illustrationResourceId = 1, youtubeVideoId = null,
                durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = emptyList(), isCompleted = false
            ),
            Exercise(
                id = "exercise2", title = "Exercise 2", description = "Desc",
                illustrationResourceId = 2, youtubeVideoId = null,
                durationSeconds = 30, difficultyLevel = DifficultyLevel.BEGINNER,
                instructions = emptyList(), isCompleted = false
            )
        )

        whenever(getExercisesBySetUseCase(setId)).thenReturn(flowOf(exercises))

        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)
        viewModel.loadExercises(setId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val progress = viewModel.completionProgress.value
        assertEquals(0.0, progress, 0.01)
    }

    @Test
    fun `should initialize with empty state`() {
        // When
        viewModel = ExerciseSetDetailViewModel(getExercisesBySetUseCase)

        // Then
        assertTrue(viewModel.exercises.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
        assertEquals(0.0, viewModel.completionProgress.value, 0.01)
    }
}
