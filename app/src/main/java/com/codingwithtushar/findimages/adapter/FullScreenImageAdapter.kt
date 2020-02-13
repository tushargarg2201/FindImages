package com.codingwithtushar.findimages.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SearchView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codingwithtushar.findimages.R
import com.codingwithtushar.findimages.models.Search
import java.util.*


class FullScreenImageAdapter (
    private val activity: Activity,
    private val imageUrls: ArrayList<Search>
) : PagerAdapter() {
    private var inflater: LayoutInflater? = null
    override fun getCount(): Int {
        return imageUrls.size
    }

    private var requestOptions: RequestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)

    init {
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imgDisplay: ImageView
        inflater = activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewLayout: View? = inflater?.inflate(
            R.layout.layout_fullscreen_items, container,
            false
        )
        imgDisplay = viewLayout?.findViewById<View>(R.id.imgDisplay) as ImageView

        Glide.with(activity)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrls[position].Poster)
            .into(imgDisplay)

        (container as ViewPager).addView(viewLayout)
        return viewLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout?)

    }

}