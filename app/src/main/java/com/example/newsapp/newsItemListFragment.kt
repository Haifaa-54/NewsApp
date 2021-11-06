package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class newsItemListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var articalTitle=newsItemListFragmentArgs.fromBundle(requireArguments()).newsTitle
        var articalImage=newsItemListFragmentArgs.fromBundle(requireArguments()).newsImage
        var articalLink=newsItemListFragmentArgs.fromBundle(requireArguments()).newsUrl

        var webView= view.findViewById<WebView>(R.id.webView)
        webView.loadUrl(articalLink)
    }
}