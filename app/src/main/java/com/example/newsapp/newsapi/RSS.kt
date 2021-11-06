package com.example.newsapp.newsapi

import androidx.room.*
//import com.supermario.backgroundtasks01.newsdb.MediaContentConverter
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "rss")
class RSS {

    @Element(name = "channel")
    var channel: Channel? = null

    @Xml
    class Channel{
        @PropertyElement
        var title: String? = null
        @Element
        var items: List<Item>? = null
    }

    @Entity
    @Xml(name = "item")
    class Item{

//        @PrimaryKey(autoGenerate = true)
//        var id: Long? = null

        @PropertyElement
        var title: String? = null
        @PropertyElement
        var pubDate: String? = null
        @PropertyElement

        var link: String? = null


        @Element(name = "media:content")
   //     @TypeConverters(MediaContentConverter::class)
        var mediaContent: MediaContent? = null
    }

    @Xml
    class MediaContent{
        @Attribute
        var url: String? = null
    }
}

