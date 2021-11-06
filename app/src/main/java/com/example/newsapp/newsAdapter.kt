package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.newsDB.NewsItem
import com.example.newsapp.newsapi.RSS
import com.makeramen.roundedimageview.RoundedImageView

class newsAdapter(var newsItem: List<NewsItem>,var listener: INewsListener):RecyclerView.Adapter<newsAdapter.NewViewHolder>() {
    class NewViewHolder(view:View):RecyclerView.ViewHolder(view){
        var newsImage=view.findViewById<RoundedImageView>(R.id.newsImage)
        var newsTitle=view.findViewById<TextView>(R.id.newsTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
      return NewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_items,parent,false))
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(newsItem.get(position).imageUrl).into(holder.newsImage)
        holder.newsTitle.text=newsItem.get(position).title
        holder.itemView.setOnClickListener {
            listener.itemViewListener(position)
        }
    }

    override fun getItemCount(): Int {
       return newsItem.size
    }
    interface INewsListener{
        public fun itemViewListener(position: Int)
    }
}