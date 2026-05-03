package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedArticleViewModel @Inject constructor() : ViewModel() {
    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle = _selectedArticle.asStateFlow()

    fun setArticle (article: Article){
        _selectedArticle.value = article
    }
}