package com.rashmi.news.ui.viewmodel

import NewsRepo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashmi.news.helpers.RetrofitClient
import com.rashmi.news.model.APIResponse
import com.rashmi.news.model.ArticlesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val repo: NewsRepo by lazy {
        NewsRepo(RetrofitClient.apiInstance)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _news = MutableLiveData<APIResponse>();
    val news: LiveData<APIResponse>
        get() = _news


    private val _selectedNewsItem = MutableLiveData<ArticlesItem?>()
    val selectedNewsItem: MutableLiveData<ArticlesItem?>
        get() = _selectedNewsItem

    init {
        getNews()
    }

    fun setNews(newsList: APIResponse) {
        _news.value = newsList
    }

    fun selectNewsItem(newsItem: ArticlesItem?) {
        _selectedNewsItem.value = newsItem
    }

    fun getNews() {
        viewModelScope.launch {
            _isLoading.value = true
            withContext(Dispatchers.IO) {
                try {
                    _news.postValue(repo.getNews())
                    println("_________News articles size: ${_news.value?.articles?.size}")
                } catch (e: Exception) {
                    println("_________in exception: ${e.localizedMessage}")
                    e.printStackTrace()
                } finally {
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}