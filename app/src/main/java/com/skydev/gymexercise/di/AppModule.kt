package com.skydev.gymexercise.di

import androidx.room.Room
import com.skydev.gymexercise.data.db.GymExDatabase
import com.skydev.gymexercise.data.paging.ExercisePagingSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        // Provide RoomDatabase
        Room.databaseBuilder(androidContext(), GymExDatabase::class.java, "gymex.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        // Provide ExerciseDao
        get<GymExDatabase>().exerciseDao()
    }
    single {
        // Provide ExercisePagingSource
        ExercisePagingSource(get())
    }
}