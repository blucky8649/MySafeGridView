package com.example.mysafegridview.presentation

import androidx.lifecycle.ViewModel
import com.example.mysafegridview.data.model.Product
import com.example.mysafegridview.data.model.SectionInfo
import com.example.mysafegridview.presentation.model.ShopViewType
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor (
    private val shopRepository: ShopRepository
): ViewModel() {
    private fun getSectionNos(): List<Int> {
        return shopRepository.getSectionNos()
    }

    private fun getProductBySectionNo(sectionNo: Int): SectionInfo {
        return shopRepository.getSectionProducts(sectionNo)
    }


    fun calcGridData() : List<ShopViewType>{
        val viewTypeList: ArrayList<ShopViewType> = ArrayList()
        getSectionNos().forEach { sectionNo ->
            getProductBySectionNo(sectionNo).also { sectionInfo ->
                viewTypeList.add(ShopViewType(sectionInfo.sectionTitle, 0))
                sectionInfo.sectionProducts.forEach { product ->
                    viewTypeList.add(ShopViewType(Gson().toJson(product), 1))
                }
                // 아이템이 홀수면 빈 값 하나 더 추가
                if (sectionInfo.sectionProducts.size % 2 == 1) {
                    viewTypeList.add(ShopViewType(Gson().toJson(Product()), 1))
                }
            }
        }
        return viewTypeList
    }
}