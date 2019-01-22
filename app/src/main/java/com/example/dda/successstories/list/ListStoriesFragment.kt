package com.example.dda.successstories.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dda.successstories.R
import com.example.dda.successstories.adapters.MainAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.list_stories_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class ListStoriesFragment : Fragment() {


    var listStories = mutableListOf<Story>()
    private lateinit var adapter: MainAdapter

    companion object {
        fun newInstance() = ListStoriesFragment()
    }

    private lateinit var viewModel: ListStoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_stories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListStoriesViewModel::class.java)
        // TODO: Use the ViewModel

        adapter = MainAdapter()
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            if (isOnline()) {
                rv.removeAllViewsInLayout()
                listStories.clear()
                getData()
            } else {
                swipeRefresh.isRefreshing = false
                Toast.makeText(this.context, "Нет подключения к интернету!", Toast.LENGTH_SHORT).show()
            }
        }

        if (viewModel.getStoriesList().isEmpty()) {
            getData()
        } else {
            adapter.set(viewModel.getStoriesList())
        }

    }

    private fun getData() {
        GlobalScope.launch {
            fetchJson()
        }
    }

    private fun fetchJson() {

        val url = "https://javarush.ru/api/1.0/rest/posts?groupKid=stories"
//        val url = "https://javarush.ru/api/1.0/rest/posts?groupKid=stories&order=POPULAR&limit=10"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                //listNews = gson.fromJson(body, kotlin.collections.mutableListOf<Story>()::class.java).toMutableList()
                listStories = gson.fromJson(body, object : TypeToken<MutableList<Story>>() {}.type)

                GlobalScope.launch(Dispatchers.Main) {
                    viewModel.setListStories(listStories)
                    adapter.set(listStories)
                    swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

        })
    }

    private fun isOnline(): Boolean {
        val connMgr = this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}
