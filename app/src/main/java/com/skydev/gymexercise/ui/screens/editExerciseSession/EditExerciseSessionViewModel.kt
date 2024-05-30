package com.skydev.gymexercise.ui.screens.editExerciseSession

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydev.gymexercise.data.model.Exercise
import com.skydev.gymexercise.domain.repo.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EditExerciseSessionViewModel(
    private val exerciseSessionRepository: ExerciseRepository
) : ViewModel() {

    var exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null)

    fun getExerciseForSession(exerciseId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exercise.value = exerciseSessionRepository.getExerciseById(exerciseId)
        }
    }

}