package com.example.compose1.domain.usecase

import com.example.compose1.domain.repository.NewsRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

}