package com.example.newsapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.TextSize
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.utils.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    val repository: NewsRepository,
    preferencesManager: UserPreferencesManager
) : ViewModel() {
    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()

    private var currentArticle : Article? = null

    val textSize: StateFlow<TextSize> = preferencesManager.selectedTextSize
        .stateIn(
            scope         = viewModelScope,
            started       = SharingStarted.WhileSubscribed(5000),
            initialValue  = TextSize.MEDIUM
        )

    fun setArticle(article: Article){
        currentArticle = article
        viewModelScope.launch {
            repository.isArticleSaved(article.url).collect { saved ->
                _isSaved.value = saved
            }
        }
    }

    fun toggleSave(){
        viewModelScope.launch {
            currentArticle?.let { article ->
                if(_isSaved.value) repository.deleteArticle(article)
                else repository.saveArticle(article)
            }
        }
    }
}