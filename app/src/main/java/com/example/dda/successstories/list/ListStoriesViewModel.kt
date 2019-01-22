package com.example.dda.successstories.list

import android.arch.lifecycle.ViewModel

class ListStoriesViewModel : ViewModel() {

    private var listStories = mutableListOf<Story>()

    fun setListStories(list: MutableList<Story>){
        if  (listStories.size > 0){
            listStories.clear()
        }
        listStories.addAll(list)
    }

    fun getStoriesList(): MutableList<Story> {
        return listStories
    }

}
