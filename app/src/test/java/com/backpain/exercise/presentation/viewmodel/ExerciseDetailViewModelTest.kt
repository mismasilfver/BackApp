package com.backpain.exercise.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backpain.exercise.domain.model.DifficultyLevel
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.usecase.GetExerciseDetailUseCase
import com.backpain.exercise.domain.usecase.MarkExerciseCompletedUseCase
import com.backpain.exercise.domain.usecase.ResetExerciseCompletionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ExerciseDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getExerciseDetailUseCase: GetExerciseDetailUseCase

    @Mock
    private lateinit var markExerciseCompletedUseCase: MarkExerciseCompletedUseCase

    @Mock
    private lateinit var resetExerciseCompletionUseCase: ResetExerciseCompletionUseCase

    private lateinit var viewModel: ExerciseDetailViewModel

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
    fun `should load exercise details on initialization`() = runTest {
        // Given
        val exerciseId = "plank_001"
        val expectedExercise = Exercise(
            id = "plank_001",
            title = "Plank",
            description = "Core strengthening exercise",
            illustrationResourceId = 2131165184,
            youtubeVideoId = "ASdvN_XEl_c",
            durationSeconds = 30,
            difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = listOf("Start in push-up position", "Hold body straight"),
            isCompleted = false
        )

        whenever(getExerciseDetailUseCase(exerciseId)).thenReturn(flowOf(expectedExercise))

        // When
        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise(exerciseId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val exercise = viewModel.exercise.value
        assertNotNull(exercise)
        assertEquals("plank_001", exercise?.id)
        assertEquals("Plank", exercise?.title)
        assertEquals(30, exercise?.durationSeconds)
        assertEquals(30, viewModel.remainingTime.value) // Should start with full duration
    }

    @Test
    fun `should start timer and countdown correctly`() = runTest {
        // Given
        val exercise = createTestExercise(durationSeconds = 5)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.startTimer()
        
        // Then - Should start with full duration
        assertEquals(5, viewModel.remainingTime.value)
        assertTrue(viewModel.isTimerRunning.value)

        // When - Advance time by 2 seconds
        testDispatcher.scheduler.advanceTimeBy(2000)
        
        // Then - Should have 3 seconds remaining
        assertEquals(3, viewModel.remainingTime.value)
        assertTrue(viewModel.isTimerRunning.value)
    }

    @Test
    fun `should stop timer when reaching zero`() = runTest {
        // Given
        val exercise = createTestExercise(durationSeconds = 3)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.startTimer()

        // When - Advance time beyond duration
        testDispatcher.scheduler.advanceTimeBy(3000)

        // Then - Timer should stop and mark as completed
        assertEquals(0, viewModel.remainingTime.value)
        assertFalse(viewModel.isTimerRunning.value)
        verify(markExerciseCompletedUseCase).invoke("test")
    }

    @Test
    fun `should pause timer correctly`() = runTest {
        // Given
        val exercise = createTestExercise(durationSeconds = 10)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.startTimer()
        testDispatcher.scheduler.advanceTimeBy(3000)

        // When
        viewModel.pauseTimer()

        // Then
        assertEquals(7, viewModel.remainingTime.value) // Should stop at 7 seconds
        assertFalse(viewModel.isTimerRunning.value)
    }

    @Test
    fun `should resume timer from paused state`() = runTest {
        // Given
        val exercise = createTestExercise(durationSeconds = 10)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.startTimer()
        testDispatcher.scheduler.advanceTimeBy(3000)
        viewModel.pauseTimer()

        // When
        viewModel.resumeTimer()
        testDispatcher.scheduler.advanceTimeBy(2000)

        // Then - Should continue from 7 seconds
        assertEquals(5, viewModel.remainingTime.value)
        assertTrue(viewModel.isTimerRunning.value)
    }

    @Test
    fun `should reset timer to original duration`() = runTest {
        // Given
        val exercise = createTestExercise(durationSeconds = 30)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.startTimer()
        testDispatcher.scheduler.advanceTimeBy(10000)

        // When
        viewModel.resetTimer()

        // Then
        assertEquals(30, viewModel.remainingTime.value)
        assertFalse(viewModel.isTimerRunning.value)
        verify(resetExerciseCompletionUseCase).invoke("test")
    }

    @Test
    fun `should handle completed exercise correctly`() = runTest {
        // Given
        val exercise = createTestExercise(isCompleted = true)
        whenever(getExerciseDetailUseCase("test")).thenReturn(flowOf(exercise))

        // When
        viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )
        viewModel.loadExercise("test")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val loadedExercise = viewModel.exercise.value
        assertNotNull(loadedExercise)
        assertTrue(loadedExercise?.isCompleted ?: false)
    }

    @Test
    fun `should format time correctly for display`() {
        // Given
        val viewModel = ExerciseDetailViewModel(
            getExerciseDetailUseCase,
            markExerciseCompletedUseCase,
            resetExerciseCompletionUseCase
        )

        // When & Then
        assertEquals("00:30", viewModel.formatTime(30))
        assertEquals("01:00", viewModel.formatTime(60))
        assertEquals("02:30", viewModel.formatTime(150))
        assertEquals("00:00", viewModel.formatTime(0))
    }

    private fun createTestExercise(
        durationSeconds: Int = 30,
        isCompleted: Boolean = false
    ): Exercise {
        return Exercise(
            id = "test",
            title = "Test Exercise",
            description = "Test description",
            illustrationResourceId = 1,
            youtubeVideoId = null,
            durationSeconds = durationSeconds,
            difficultyLevel = DifficultyLevel.BEGINNER,
            instructions = listOf("Test instruction"),
            isCompleted = isCompleted
        )
    }
}
