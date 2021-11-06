package com.example.newsapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapp.newsDB.NewsDao
import com.example.newsapp.newsDB.NewsItem
import com.example.newsapp.newsapi.NewsApi
import com.example.newsapp.newsapi.NewsResponse
import com.example.newsapp.utility.NotificationUtility
import com.supermario.newsappservice.newsdb.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MyTask(context: Context,workerParameters: WorkerParameters) :CoroutineWorker(context,workerParameters){
    lateinit var newsApi: NewsApi
    lateinit var newsDao: NewsDao
    lateinit var notificationUtility: NotificationUtility
    companion object{
        public var isRunning=false
        public var newsItemArray=ArrayList<NewsItem>()
    }
    init {
        newsApi= NewsApi("https://feeds.alwatanvoice.com/ar/")

    }
    override suspend fun doWork(): Result {
        var result= withContext(Dispatchers.IO) {
            try {
                newsDao = NewsDatabase.getDatabase(applicationContext)!!.getNewsDao()
                notificationUtility = NotificationUtility(applicationContext)
                longTask()
                worldNews()
                islamicNews()
                entertainmentNews()
            } catch (e: Exception) {
                return@withContext Result.failure()
            }
            return@withContext Result.success()
        }
        return result
    }
    public suspend fun longTask(){

        withContext(Dispatchers.Main) {
            newsApi.getNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        Log.d("ttt", it.title!!)
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                var newsItem= NewsItem.creatFromRSSItem(it,1)
                                 newsItemArray.add(newsItem)
//                                       NewsItem().apply {
//                                       this.link=it.link
//                                       this.title=it.title
//                                       this.pubDate=it.pubDate
//                                       this.category=1
//                                       this.imageUrl=it.mediaContent?.url
//
//                                   }
//


                                var inserted= newsDao.insertNewsItem(newsItem)

                                if(inserted !=-1L){

                                    Log.d("ttt","inserted to db")

                                    notificationUtility.sendNotification(inserted.toInt(),"أخبار فلسطينية",newsItem.title.toString(),newsItem.link.toString())
                                   // GlobalScope.launch(Dispatchers.Main) {  PalestinianNewsFragment.adapter.notifyDataSetChanged() }
                                }

                            }
                        }
                    }

                } else {
                    Log.d("ttt", "Error")
                }
            })
        }




    }
    public suspend fun worldNews(){
        withContext(Dispatchers.Main) {
            newsApi.getWorldNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                var newsItem=NewsItem.creatFromRSSItem(it,2)
                                //   newsItemArray.add(newsItem)

                                var inserted= newsDao.insertNewsItem(newsItem)
                                if(inserted !=-1L){
                                    notificationUtility.sendNotification(inserted.toInt()," أخبار دولية - دنيا الوطن",newsItem.title.toString(),newsItem.link.toString())
                                }
                            }
                        }
                    }
                } else {
                    Log.d("ttt", "Error")
                }
            })

        }

    }

    public suspend fun islamicNews(){

        withContext(Dispatchers.Main) {
            newsApi.getIslamicNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                var newsItem=NewsItem.creatFromRSSItem(it,3)
                                //   newsItemArray.add(newsItem)

                                var inserted= newsDao.insertNewsItem(newsItem)
                                if(inserted !=-1L){
                                    notificationUtility.sendNotification(inserted.toInt(),"اسلاميات - دنيا الوطن",newsItem.title.toString(),newsItem.link.toString())
                                }
                            }
                        }
                    }
                } else {
                    Log.d("ttt", "Error")
                }
            })

        }
    }
    public suspend fun entertainmentNews(){
        withContext(Dispatchers.Main) {
            newsApi.getEntertainmentNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                var newsItem=NewsItem.creatFromRSSItem(it,4)
                                //   newsItemArray.add(newsItem)

                                var inserted= newsDao.insertNewsItem(newsItem)
                                if(inserted !=-1L){
                                    notificationUtility.sendNotification(inserted.toInt(),"أخبار ترفيهية - دنيا الوطن",newsItem.title.toString(),newsItem.link.toString())
                                }
                            }
                        }
                    }
                } else {
                    Log.d("ttt", "Error")
                }
            })

        }
    }
}