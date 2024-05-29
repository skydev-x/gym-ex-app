package com.skydev.gymexercise.di

import com.skydev.gymexercise.domain.repo.ExerciseRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExerciseRepository> {
        // Provide ExerciseRepository
        ExerciseRepository(get(), get())
    }
}