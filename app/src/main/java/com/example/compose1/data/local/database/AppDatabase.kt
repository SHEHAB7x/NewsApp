package com.example.compose1.data.local.database

import androidx.room.Database
import com.example.compose1.data.local.dao.ArticleDao
import com.example.compose1.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase {
    abstract fun articleDao(): ArticleDao

    companion object{
        const val DATABASE_NAME = "news_db"
    }
}