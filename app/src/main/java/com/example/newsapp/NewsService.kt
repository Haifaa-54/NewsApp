package com.example.newsapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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

class NewsService : Service() {
    private var delay=10*1000L
    lateinit var newsApi:NewsApi
    lateinit var newsDao: NewsDao
    lateinit var notificationUtility:NotificationUtility


    companion object{
        public var serviceRunning=false
       public var newsItemArray=ArrayList<NewsItem>()

    }
init {
    newsApi= NewsApi("https://feeds.alwatanvoice.com/ar/")

}
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        newsDao=NewsDatabase.getDatabase(applicationContext)!!.getNewsDao()
         notificationUtility= NotificationUtility(applicationContext)
        var notification= NotificationCompat.Builder(applicationContext,NotificationUtility.CHANNEL_ID)
           .setSmallIcon(R.drawable.ic_launcher_background)
           .setContentTitle("New Servise")
           .setContentText("New Service is Runnig in Background")
           .build()
        startForeground(Int.MAX_VALUE,notification)
        serviceRunning=true
        GlobalScope.launch(Dispatchers.IO){
            while (true) {
             //   var url = intent?.getStringExtra("url")
                //       Log.d("ttt",url!!)
                worldNews()
                    longTask()

                islamicNews()
                entertainmentNews()
                //longTask()
                // stopSelf()
              //  Thread.sleep(delay)
            }
        }
     //   stopSelf()
        return Service.START_STICKY //لو عمل كراش بتخليه يرجع يعيد تشغيل مع التطبيق
       // return Service.START_REDELIVER_INTENT //رجعها مع تشغيل الانتنت
       // return super.onStartCommand(intent, flags, startId)
    }

    public suspend fun longTask(){
        withContext(Dispatchers.Main) {
            newsApi.getNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                var newsItem=NewsItem.creatFromRSSItem(it,1)
                                    newsItemArray.add(newsItem)

      //                          var myToken="fgfg"
//                                       NewsItem().apply {
//                                       this.link=it.link
//                                       this.title=it.title
//                                       this.pubDate=it.pubDate
//                                       this.category=1
//                                       this.imageUrl=it.mediaContent?.url
//                                   }

                               var inserted= newsDao.insertNewsItem(newsItem)
                                if(inserted !=-1L){
                                    notificationUtility.sendNotification(inserted.toInt(),"أخبار محلية - دنيا الوطن",newsItem.title.toString(),newsItem.link.toString())
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

    override fun onDestroy() {
        serviceRunning=false
        super.onDestroy()

    }



}