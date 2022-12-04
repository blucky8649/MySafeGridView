package com.example.mysafegridview.data.model

data class SectionInfo(
    val sectionNo: Int = -1,
    val sectionTitle: String = "",
    val sectionProducts: List<Product> = listOf()
)