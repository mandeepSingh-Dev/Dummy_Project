package com.mandeep.dummyproject.Retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mandeep.dummyproject.Pagination.RetrofitPagingSource
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItem
import com.mandeep.dummyproject.Retrofit.DataClasses.PhotoItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetrofitViewModel @Inject constructor(val retrofitRepositry: RetrofitRepositry ):ViewModel() {

    val liveList  = MutableLiveData<PhotoItemList>()

    val _text = MutableLiveData<String>()
    val text : LiveData<String>
    get() = _text


    val pager = Pager(PagingConfig(pageSize = 10)){
        RetrofitPagingSource(retrofitRepositry)
    }.flow.cachedIn(viewModelScope)

    init{
        viewModelScope.launch {
            liveList.value = retrofitRepositry.fetchData(1)
        }
    }

    fun getPhotoItemPagination() = Pager(PagingConfig(pageSize = 20)){
            RetrofitPagingSource(retrofitRepositry)
        }.flow/*.cachedIn(viewModelScope)*/


    fun setLiveText(text:String){
        _text.value = text
    }
}