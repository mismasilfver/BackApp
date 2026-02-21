package com.backpain.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backpain.exercise.domain.model.ExerciseSet
import com.backpain.exercise.domain.usecase.GetExerciseSetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExerciseSetsUseCase: GetExerciseSetsUseCase
) : ViewModel() {

    private val _exerciseSets = MutableStateFlow<List<ExerciseSet>>(emptyList())
    val exerciseSets: StateFlow<List<ExerciseSet>> = _exerciseSets.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadExerciseSets()
    }

    fun loadExerciseSets() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getExerciseSetsUseCase().collect { exerciseSets ->
                    _exerciseSets.value = exerciseSets
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // TODO: Implement proper error handling
                _isLoading.value = false
            }
        }
    }

    fun refreshExerciseSets() {
        loadExerciseSets()
    }
}
