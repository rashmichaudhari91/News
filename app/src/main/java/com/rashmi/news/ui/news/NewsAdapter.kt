package com.rashmi.news.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableRow
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rashmi.news.R
import com.rashmi.news.helpers.IConstant
import com.rashmi.news.model.ArticlesItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NewsAdapter(private val newsList: List<ArticlesItem>) : BaseAdapter() {

    override fun getCount(): Int = newsList.size
    override fun getItem(position: Int): Any = newsList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val article = getItem(position) as ArticlesItem
        val context = parent?.context

        val tableRow = LayoutInflater.from(context).inflate(R.layout.adapter_news, null) as TableRow

        Glide.with(context!!)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_menu_camera)
            .into(tableRow.findViewById(R.id.newsImage))

        tableRow.findViewById<TextView>(R.id.newsTitle).text = article.title
        tableRow.findViewById<TextView>(R.id.newsDescription).text = article.description
        val formattedDate: String = article.publishedAt?.let { formatDate(it) } ?: "N/A"
        tableRow.findViewById<TextView>(R.id.newsPublishedDate).text = formattedDate
        return tableRow
    }

    private fun formatDate(inputDateString: String): String? {
        return try {
            val inputFormat = SimpleDateFormat(IConstant.dateFormat, Locale.getDefault())
            val date: Date = inputFormat.parse(inputDateString)
            val outputFormat = SimpleDateFormat(IConstant.requiredDateFormat, Locale.getDefault())
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}