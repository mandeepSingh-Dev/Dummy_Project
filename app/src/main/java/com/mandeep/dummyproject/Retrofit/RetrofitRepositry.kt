package com.mandeep.dummyproject.Retrofit

import javax.inject.Inject


class RetrofitRepositry @Inject constructor(val apiService: ApiService) {

    suspend fun fetchData(page:Int) = apiService.fetchData(page=page)
}