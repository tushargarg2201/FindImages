package com.codingwithtushar.findimages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codingwithtushar.findimages.R
import com.codingwithtushar.findimages.models.Search

class SearchListAdapter (var items: List<Search>, private val listener: ListenerInterface)
    : RecyclerView.Adapter<SearchListAdapter.ViewHolder> () {

    private var requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

    init {
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_search_items, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(items[position].Poster)
            .into(holder.searchImageView)

        holder.searchTitle.text = items[position].Title
    }

    fun setData(itemList: List<Search>) {
        items = itemList
    }

    class ViewHolder(itemView: View, listener: ListenerInterface) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var searchTitle: TextView = itemView.findViewById(R.id.search_title)
        var searchImageView: ImageView = itemView.findViewById(R.id.search_image_view)

        private val clickListener = listener

        init {
            searchImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.search_image_view) {
                clickListener.onClickEvent(adapterPosition)
            }
        }
    }

    interface ListenerInterface {
        fun onClickEvent(position: Int)
    }
}