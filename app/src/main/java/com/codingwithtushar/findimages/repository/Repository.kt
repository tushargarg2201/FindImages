package com.codingwithtushar.findimages.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingwithtushar.findimages.models.Search
import com.codingwithtushar.findimages.networkrepository.ApiClient
import java.util.concurrent.Executors

open class Repository() {

    private val apiClient: ApiClient = ApiClient()
    private val executors = Executors.newSingleThreadExecutor()

    fun getSearchList(): MutableLiveData<List<Search>> {
        return apiClient.getSearchList()
    }

    fun startSearch(query: String, pageNumber: Int) {
        apiClient.startSearch(query, pageNumber)
    }

}