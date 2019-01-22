package com.example.dda.successstories.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dda.successstories.R
import com.example.dda.successstories.holders.FooterViewHolder
import com.example.dda.successstories.holders.MyViewHolder
import com.example.dda.successstories.list.Story

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listStories = mutableListOf<Story>()

    //for "load more"...
    private var FOOTER_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FOOTER_VIEW -> FooterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.tv_swipe, parent, false)
            )
            else -> MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        if (listStories.size == 0) {
            return 1
        }
        return listStories.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == listStories.size) {
            return FOOTER_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        try {
            if (holder is MyViewHolder) {
                holder.bind(listStories[pos])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun set(list: MutableList<Story>) {
        listStories.clear()
        listStories.addAll(list)
        notifyDataSetChanged()
    }

}