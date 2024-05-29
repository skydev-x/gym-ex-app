package com.skydev.gymexercise.di

import com.skydev.gymexercise.ui.screens.home.HomeViewModel
import com.skydev.gymexercise.ui.screens.workout.WorkoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WorkoutViewModel()
    }
    viewModel {
        // Provide HomeViewModel
        HomeViewModel(get(), get())
    }
}