package com.mandeep.dummyproject.Retrofit

import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItemList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/list?limit=50")
    suspend fun fetchData(@Query("page") page:Int):PhotoItemList
}