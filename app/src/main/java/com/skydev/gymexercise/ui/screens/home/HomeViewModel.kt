package com.skydev.gymexercise.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skydev.gymexercise.data.model.Exercise
import com.skydev.gymexercise.domain.repo.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class HomeViewModel(
    private val appContext: Context,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {


    fun getAllExercises() =
        exerciseRepository.getExerciseFlow().cachedIn(viewModelScope)


    var exerciseList = MutableStateFlow<List<Exercise>>(listOf())


    fun getAllExercisesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseList.value = exerciseRepository.getAll()
        }
    }


    @OptIn(InternalSerializationApi::class)
    fun loadExercisesInDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val exercises = appContext.assets.open("exercises.json").bufferedReader().use {
                it.readText()
            }
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            val exercisesList =
                json.decodeFromString(ListSerializer(Exercise::class.serializer()), exercises)
            exerciseRepository.insertAll(exercisesList)
        }
    }

}