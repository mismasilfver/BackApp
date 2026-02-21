package com.backpain.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.usecase.GetExerciseDetailUseCase
import com.backpain.exercise.domain.usecase.MarkExerciseCompletedUseCase
import com.backpain.exercise.domain.usecase.ResetExerciseCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase,
    private val markExerciseCompletedUseCase: MarkExerciseCompletedUseCase,
    private val resetExerciseCompletionUseCase: ResetExerciseCompletionUseCase
) : ViewModel() {

    private val _exercise = MutableStateFlow<Exercise?>(null)
    val exercise: StateFlow<Exercise?> = _exercise.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _remainingTime = MutableStateFlow(0)
    val remainingTime: StateFlow<Int> = _remainingTime.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    fun loadExercise(exerciseId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getExerciseDetailUseCase(exerciseId).collect { exercise ->
                    _exercise.value = exercise
                    _remainingTime.value = exercise?.durationSeconds ?: 0
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // TODO: Implement proper error handling
                _isLoading.value = false
            }
        }
    }

    fun startTimer() {
        if (_isTimerRunning.value) return

        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while (_remainingTime.value > 0) {
                delay(1000)
                if (_remainingTime.value > 0) {
                    _remainingTime.value -= 1
                }
            }
            
            // Timer completed
            _isTimerRunning.value = false
            _exercise.value?.let { exercise ->
                markExerciseCompletedUseCase.invoke(exercise.id)
                // Update local state to reflect completion
                _exercise.value = exercise.copy(isCompleted = true)
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _isTimerRunning.value = false
    }

    fun resumeTimer() {
        if (_remainingTime.value > 0 && !_isTimerRunning.value) {
            startTimer()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        _exercise.value?.let { exercise ->
            _remainingTime.value = exercise.durationSeconds
            resetExerciseCompletionUseCase.invoke(exercise.id)
            // Update local state to reflect reset
            _exercise.value = exercise.copy(isCompleted = false)
        }
    }

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
