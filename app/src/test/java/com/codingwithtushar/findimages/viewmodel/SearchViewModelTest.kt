package com.codingwithtushar.findimages.viewmodel

import androidx.lifecycle.MutableLiveData
import com.codingwithtushar.findimages.models.Search
import com.codingwithtushar.findimages.networkrepository.ApiClient
import com.codingwithtushar.findimages.repository.Repository
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private val searchList = listOf<Search>(
        Search("Friends", "1994–2004", "tt0108778", "series",
            "https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg"),
        Search("Friends with Benefits", "2011", "tt1632708", "movie",
            "https://m.media-amazon.com/images/M/MV5BMTQ2MzQ0NTk4N15BMl5BanBnXkFtZTcwMDc2NDYzNQ@@._V1_SX300.jpg")
    )

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var apiClient: ApiClient

    @Spy
    private val searchListLiveData: MutableLiveData<List<Search>> = MutableLiveData()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Before
    fun setUp() {
        viewModel = SearchViewModel()
    }

    @Test
    fun test_list_is_null() {
        val list = viewModel.getSearchList()
        println("list value--->" + list.value)
        assertNull(viewModel.getSearchList().value)
    }

    @Test
    fun test_list_is_not_null() {

    }

    @After
    fun tearDown() {
    }
}