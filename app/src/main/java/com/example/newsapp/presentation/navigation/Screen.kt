package com.example.newsapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home          : Screen("home")
    object Favorites     : Screen("favorites")
    object Search        : Screen("search")
    object ArticleDetails : Screen("article_detail")
}