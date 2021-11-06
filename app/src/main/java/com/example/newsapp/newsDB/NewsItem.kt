package com.example.newsapp.newsDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.newsapi.RSS

@Entity
class NewsItem {
    @PrimaryKey(autoGenerate = true)
    var id:Long?=null
    var title:String?=null
    var link:String?=null
    var pubDate:String?=null
    var imageUrl:String?=null
    var category:Int?=null
    companion object{
    public fun creatFromRSSItem(it: RSS.Item,category:Int):NewsItem{
        var newsItem=NewsItem()
        newsItem.apply {
            this.link=it.link
            this.title=it.title
            this.pubDate=it.pubDate
            this.category=category
            this.imageUrl=it.mediaContent?.url
        }
        return newsItem
    }
    }
}