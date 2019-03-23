package com.mansoul.mvvm.data.entity

data class GankToday(
    val category: List<String>,
    val error: Boolean,
    val results: Results
)