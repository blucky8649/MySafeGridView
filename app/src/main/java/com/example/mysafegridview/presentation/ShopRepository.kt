package com.example.mysafegridview.presentation

import com.example.mysafegridview.data.model.SectionInfo

interface ShopRepository {
    fun getSectionNos(): List<Int>
    fun getSectionProducts(sectionNo: Int): SectionInfo
}