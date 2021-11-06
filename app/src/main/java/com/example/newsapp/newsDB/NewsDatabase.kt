package com.supermario.newsappservice.newsdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.newsDB.NewsDao
import com.example.newsapp.newsDB.NewsItem
import com.example.newsapp.newsapi.RSS


@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){
    companion object{
        private var instance: NewsDatabase? = null
        public fun getDatabase(context: Context): NewsDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(context, NewsDatabase::class.java, "newsDB")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

    public abstract fun getNewsDao(): NewsDao
}