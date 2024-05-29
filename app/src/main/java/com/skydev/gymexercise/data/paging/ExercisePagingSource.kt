package com.skydev.gymexercise.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydev.gymexercise.data.db.dao.ExerciseDao
import com.skydev.gymexercise.data.model.Exercise

class ExercisePagingSource(
    private val dao: ExerciseDao
) : PagingSource<Int, Exercise>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exercise> {
        val page = params.key ?: 0

        return try {
            val entities = dao.getPagedList(params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Exercise>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}