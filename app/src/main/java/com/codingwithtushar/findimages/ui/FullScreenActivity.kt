package com.codingwithtushar.findimages.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.codingwithtushar.findimages.R
import com.codingwithtushar.findimages.adapter.FullScreenImageAdapter
import com.codingwithtushar.findimages.models.Search
import com.codingwithtushar.findimages.utils.Constant
import com.codingwithtushar.findimages.utils.TopExceptionHandler


private  lateinit var adapter: FullScreenImageAdapter
private lateinit var viewPager: ViewPager

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler(this))
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler(this))
        setContentView(R.layout.layout_fullscreen)
        viewPager = findViewById<View>(R.id.pager) as ViewPager

        val arrayList = intent.getParcelableArrayListExtra<Search>(Constant.ARRAY_LIST) as ArrayList<Search>
        val position = intent.getIntExtra(Constant.POSITION, 0)
        viewPager.adapter = FullScreenImageAdapter(this, arrayList);
        viewPager.currentItem = position

    }
}