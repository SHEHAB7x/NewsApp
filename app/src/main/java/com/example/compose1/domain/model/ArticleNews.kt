package com.example.compose1.domain.model

data class ArticleNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)