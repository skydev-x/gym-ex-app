package com.skydev.gymexercise.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydev.gymexercise.data.db.dao.ExerciseDao
import com.skydev.gymexercise.data.model.Exercise
import com.skydev.gymexercise.data.paging.ExercisePagingSource
import com.skydev.gymexercise.data.repo.ExerciseRepo
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val exercisePagingSource: ExercisePagingSource
) : ExerciseRepo {
    override fun getAll() = exerciseDao.getAll()


    fun getExerciseFlow(): Flow<PagingData<Exercise>> {
        val data = Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
        ) {
            ExercisePagingSource(exerciseDao)
        }.flow
        return data
    }

    override fun getExerciseById(id: String) {
        exerciseDao.getExerciseById(id)
    }

    override fun insertAll(exercises: List<Exercise>) {
        exerciseDao.insertAll(exercises)
    }

    override fun findExerciseByName(name: String) {
        exerciseDao.findExerciseByName(name)
    }

    override fun deleteAll() {
        exerciseDao.deleteAll()
    }

    override fun delete(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }


    override fun deleteById(id: String) {
        exerciseDao.deleteById(id)
    }
}