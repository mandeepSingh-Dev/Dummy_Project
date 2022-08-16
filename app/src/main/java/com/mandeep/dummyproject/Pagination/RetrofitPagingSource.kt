package com.mandeep.dummyproject.Pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import com.mandeep.dummyproject.Retrofit.RetrofitRepositry
import java.lang.Exception
import javax.inject.Inject

class RetrofitPagingSource(val retrofitRepositry: RetrofitRepositry) : PagingSource<Int,PhotoItem>()
{
    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {

        return try {
            val page = params.key ?: 1

            val photoItemList = retrofitRepositry.apiService.fetchData(page)
            Log.d("vidnfd",photoItemList.size.toString())

            val prevKey = if (page == 1) null else page.minus(1)
            val nextKey = if (photoItemList.isEmpty()) null else page.plus(1)

            val data = LoadResult.Page(data = photoItemList, prevKey = prevKey, nextKey = nextKey)
            Log.d("foiidnf",data.data.size.toString())
            data
           }catch (e:Exception) {
               LoadResult.Error(e)
           }

    }
}