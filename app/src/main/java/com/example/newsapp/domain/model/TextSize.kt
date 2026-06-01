package com.example.newsapp.domain.model

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextSize(
    val label: String,
    val bodySize: TextUnit,
    val titleSize: TextUnit,
    val descriptionSize: TextUnit
) {
    SMALL(
        label = "Small",
        bodySize = 13.sp,
        titleSize = 18.sp,
        descriptionSize = 13.sp
    ),
    MEDIUM(
        label           = "Medium",
        bodySize        = 15.sp,
        titleSize       = 20.sp,
        descriptionSize = 15.sp
    ),
    LARGE(
        label           = "Large",
        bodySize        = 18.sp,
        titleSize       = 24.sp,
        descriptionSize = 17.sp
    );

    companion object {
        fun fromLabel(label: String): TextSize =
            TextSize.entries.find { it.label == label } ?: MEDIUM
    }
}