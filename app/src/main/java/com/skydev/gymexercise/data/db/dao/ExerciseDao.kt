package com.skydev.gymexercise.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skydev.gymexercise.data.model.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPagedList(limit: Int, offset: Int) : List<Exercise>

    @Query("SELECT * FROM exercise")
    fun getAll(): List<Exercise>

    @Query("SELECT * FROM exercise WHERE id = :id")
    fun getExerciseById(id: String): Exercise

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(exercises: List<Exercise>)

    @Query("SELECT * FROM exercise WHERE name = :name")
    fun findExerciseByName(name: String): Exercise

    @Query("DELETE FROM exercise")
    fun deleteAll()

    @Delete
    fun delete(exercise: Exercise)

    @Query("DELETE FROM exercise WHERE id = :id")
    fun deleteById(id: String)

}