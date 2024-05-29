package com.skydev.gymexercise.data.repo

import com.skydev.gymexercise.data.model.Exercise

interface ExerciseRepo {
    fun getAll() : List<Exercise>
    fun getExerciseById(id: String)
    fun insertAll(exercises: List<Exercise>)
    fun findExerciseByName(name: String)
    fun deleteAll()
    fun delete(exercise: Exercise)
    fun deleteById(id: String)
}