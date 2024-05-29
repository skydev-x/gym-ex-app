package com.skydev.gymexercise.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skydev.gymexercise.data.db.dao.ExerciseDao
import com.skydev.gymexercise.data.model.Exercise


@Database(entities = [Exercise::class], version = 1)
@TypeConverters(Converters::class)
abstract class GymExDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

}