package com.backpain.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.usecase.GetExercisesBySetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseSetDetailViewModel @Inject constructor(
    private val getExercisesBySetUseCase: GetExercisesBySetUseCase
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val completionProgress: StateFlow<Double> = _exercises.map { exercises ->
        if (exercises.isEmpty()) {
            0.0
        } else {
            val completedCount = exercises.count { it.isCompleted }
            (completedCount.toDouble() / exercises.size) * 100
        }
    }.asStateFlow(initialValue = 0.0)

    fun loadExercises(setId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getExercisesBySetUseCase(setId).collect { exercises ->
                    _exercises.value = exercises
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // TODO: Implement proper error handling
                _isLoading.value = false
            }
        }
    }

    fun refreshExercises(setId: String) {
        loadExercises(setId)
    }
}
