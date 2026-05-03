package com.example.newsapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.theme.DarkGray
import com.example.newsapp.presentation.theme.LightGray
import com.example.newsapp.presentation.theme.Primary
import com.example.newsapp.presentation.theme.Secondary
import com.example.newsapp.presentation.theme.White
import java.net.URLDecoder

@Composable
fun ArticleDetailScreen(
    articleUrl : String,
    article: Article?,
    onBackClick : () -> Unit,
    viewModel: ArticleDetailsViewModel = hiltViewModel()
){
    val isSaved by viewModel.isSaved.collectAsState()
    val decodedUrl = URLDecoder.decode(articleUrl, "UTF-8")

    LaunchedEffect(article) {
        article?.let {
            viewModel.setArticle(it)
        }
    }

    if(article == null){
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = Primary)
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(White)){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)){
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(12.dp)
                ){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = article.publishedAt,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                        Text(
                            text = article.title,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                        article.author?.let {
                            Text(
                                text = it,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(36.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable{ onBackClick() }
                        .align(Alignment.TopStart),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Text(
                    text = article.source.name,
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                article.description?.let {
                    Text(
                        text = it,
                        color = DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                }

                HorizontalDivider(color = LightGray)

                article.content?.let {
                    Text(
                        text       = it,
                        color      = DarkGray,
                        fontSize   = 14.sp,
                        lineHeight = 22.sp
                    )
                }

                Text(
                    text     = "Read full article →",
                    color    = Secondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* open browser */ }
                )

                Spacer(modifier = Modifier.height(80.dp))
            }

            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary)
                    .clickable{ viewModel.toggleSave() },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isSaved) "Remove from favorites" else "Add to favorites",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}