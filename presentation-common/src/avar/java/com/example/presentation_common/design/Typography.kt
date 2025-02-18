package com.example.presentation_common.design

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.presentation_common.R

val interFamily = FontFamily(
    Font(resId = R.font.inter_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.inter_medium, weight = FontWeight.Medium),
    Font(resId = R.font.inter_regular, weight = FontWeight.Normal)
)

internal val PubDictTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium
    ),
    headlineMedium = TextStyle(
        fontSize = 26.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.SemiBold
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium
    ),
    labelLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.SemiBold
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.SemiBold
    )
)