package com.skydev.gymexercise.di

import com.skydev.gymexercise.ui.screens.workout.WorkoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        WorkoutViewModel()
    }
}