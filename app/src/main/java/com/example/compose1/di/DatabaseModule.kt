package com.example.compose1.di

import android.content.Context
import androidx.room.Room
import com.example.compose1.data.local.dao.ArticleDao
import com.example.compose1.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Data
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase): ArticleDao =
        database.articleDao()
}