package com.codingwithtushar.findimages.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingwithtushar.findimages.R
import com.codingwithtushar.findimages.adapter.SearchListAdapter
import com.codingwithtushar.findimages.models.Search
import com.codingwithtushar.findimages.utils.Constant
import com.codingwithtushar.findimages.utils.TopExceptionHandler
import com.codingwithtushar.findimages.utils.VerticalItemDecorator
import com.codingwithtushar.findimages.viewmodel.SearchViewModel

class SearchListActivity : AppCompatActivity(), SearchListAdapter.ListenerInterface {

    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var searchView: SearchView
    private lateinit var verticalRecyclerView: RecyclerView;
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var noSearchFoundView: TextView

    private var searchList = ArrayList<Search>()
    private var pageNumber = 1
    private var searchQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler(this))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_search_list)

        searchView = findViewById(R.id.search_view)
        verticalRecyclerView = findViewById(R.id.recyclerview_vertical)
        progressBar = findViewById(R.id.loading_progress_bar)
        noSearchFoundView = findViewById(R.id.no_search_found_view)
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        initVerticalRecyclerView()
        initSearchView()
        subscribeObservers()

    }

    private fun initSearchView() {
        showProgressBar(true)
        noSearchFoundView.visibility = View.GONE
        searchQuery = "Friends"
        searchViewModel.startSearch(searchQuery, 1)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (!TextUtils.isEmpty(newText) && newText.length >= Constant.SEARCH_LENGTH) {
                    Handler().postDelayed({
                        searchQuery = newText
                        noSearchFoundView.visibility = View.GONE
                        searchViewModel.startSearch(newText, 1)
                    }, 500)

                }
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = query
                noSearchFoundView.visibility = View.GONE
                searchViewModel.startSearch(query, 1)
                return true
            }

        })
    }

    private fun initVerticalRecyclerView() {
        searchListAdapter = SearchListAdapter(searchList, this)
        val verticalItemDecorator = VerticalItemDecorator(10)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this)
        verticalRecyclerView.apply {
            addItemDecoration(verticalItemDecorator)
            adapter = searchListAdapter
        }
        verticalRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!verticalRecyclerView.canScrollVertically(1)) {
                    startNextPageQuery()
                }
            }
        })

        verticalRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    hideKeyBoard()
                }
            }
        })
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }
    private fun startNextPageQuery() {
        if (!TextUtils.isEmpty(searchQuery)) {
            showProgressBar(true)
            pageNumber++;
            noSearchFoundView.visibility = View.GONE
            searchViewModel.startSearch(searchQuery, pageNumber)
        }
    }

    private fun subscribeObservers() {
        searchViewModel.getSearchList().observe(this, Observer {
            if (it.isNotEmpty()) {
                searchList = it as ArrayList<Search>

            }
            searchListAdapter.apply {
                setData(it)
                notifyDataSetChanged()
            }
            showProgressBar(false)
            if (searchList.isEmpty()) {
                noSearchFoundView.visibility = View.VISIBLE
            } else {
                noSearchFoundView.visibility = View.GONE
            }
        })

    }

    override fun onClickEvent(position: Int) {
        val intent = Intent(this, FullScreenActivity::class.java)
        intent.putExtra(Constant.ITEM_SELECTED, searchList[position])
        intent.putParcelableArrayListExtra(Constant.ARRAY_LIST, searchList)
        intent.putExtra(Constant.POSITION, position)
        startActivity(intent)
    }

    private fun showProgressBar(show: Boolean) {
        if (show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE

    }
}
