package com.codingwithtushar.findimages.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithtushar.findimages.models.Search
import com.codingwithtushar.findimages.repository.Repository

open class SearchViewModel: ViewModel() {

    private var repository = Repository()

    fun getSearchList(): MutableLiveData<List<Search>> {
        return repository.getSearchList()
    }

    fun startSearch(query: String, pageNumber: Int) {
        repository.startSearch(query, pageNumber)
    }

}