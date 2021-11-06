package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.newsDB.NewsItem
import com.example.newsapp.newsapi.NewsApi
import com.example.newsapp.newsapi.NewsResponse
import com.example.newsapp.newsapi.RSS
import com.supermario.newsappservice.newsdb.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EntertainmentFragment : Fragment() {
 //   var newsItemArray=ArrayList<RSS.Item>()
    var newsArray=ArrayList<NewsItem>()
    lateinit var adapter:newsAdapter

    lateinit var newsApi: NewsApi


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {var data=
            NewsDatabase.getDatabase(view.context)?.getNewsDao()?.getNewsItems(4,20)
            withContext(Dispatchers.Main){
                data?.forEach {
                    newsArray.add(it)
                    adapter.notifyDataSetChanged()
                }
            }

        }
//        newsApi= NewsApi("https://feeds.alwatanvoice.com/ar/")
//        newsApi.getEntertainmentNews().observeForever(Observer{
//            if (it.status == NewsResponse.Result.SUCCESS){
//                var newsItems = it.response?.channel?.items
//                newsItems?.forEach {
//                    newsItemArray.add(it)
////                    adapter.notifyDataSetChanged()
//                }
//            }
//
//        })
        var rv=view.findViewById<RecyclerView>(R.id.entertainmentNewsRV)
        adapter= newsAdapter(newsArray,object:newsAdapter.INewsListener{
            override fun itemViewListener(position: Int) {
                var articalTitle=newsArray.get(position).title.toString()
                var imageUrl= newsArray.get(position).imageUrl.toString()
                var articalUrl= newsArray.get(position).link.toString()
                findNavController().navigate(EntertainmentFragmentDirections.actionEntertainmentFragmentToNewsItemListFragment(articalTitle,imageUrl,articalUrl))
            }

        })
        rv.adapter=adapter
        rv.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
        adapter.notifyDataSetChanged()
    }
}