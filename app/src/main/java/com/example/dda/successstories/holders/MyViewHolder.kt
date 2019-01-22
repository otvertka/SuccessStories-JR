package com.example.dda.successstories.holders

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.dda.successstories.list.ListStoriesFragmentDirections
import com.example.dda.successstories.list.Story
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*
import java.text.SimpleDateFormat


class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {

    private val title: TextView = v.row_tv_title
    private val desc: TextView = v.row_tv_desc
    private val image: ImageView = v.row_img
    private val views: TextView = v.row_tv_views
    private val createdTime: TextView = v.row_tv_createdTime

    private var titleString = ""
    private var imageUrl = ""
    private var content = ""
    private var date = ""

    init {
        v.setOnClickListener {
            v.findNavController().navigate(ListStoriesFragmentDirections.toDetailsStoriesFragment()
                .setTitle(titleString)
                .setImageUrl(imageUrl)
                .setContent(content))
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun bind(story: Story) {
        titleString = story.title
        content = story.content

        imageUrl = if(story.pictureUrl.contains("https")){
            story.pictureUrl
        } else {
            "https://javarush.ru" + story.pictureUrl
        }
        title.text = titleString

        desc.text = story.description
        Picasso.get()
            .load(imageUrl)
            .into(image)
        views.text = story.views

        date = SimpleDateFormat("d.MM.yyy").format(story.createdTime.toLong())
        createdTime.text = date

    }
}