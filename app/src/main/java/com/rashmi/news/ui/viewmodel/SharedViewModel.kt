package com.rashmi.news.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rashmi.news.model.ArticlesItem

class SharedViewModel : ViewModel() {
    val selectedNewsItem = MutableLiveData<ArticlesItem>()
}