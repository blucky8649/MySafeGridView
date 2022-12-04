package com.example.mysafegridview.data.repository

import com.example.mysafegridview.data.model.Product
import com.example.mysafegridview.data.model.SectionInfo
import com.example.mysafegridview.presentation.ShopRepository

class ShopRepositoryImpl: ShopRepository {
    override fun getSectionNos(): List<Int> {
        return listOf(1, 2, 3, 4, 5)
    }

    override fun getSectionProducts(sectionNo: Int): SectionInfo {
        return when(sectionNo) {
            1 -> {
                SectionInfo(
                    sectionNo = 1,
                    sectionTitle = "겨울 방한 액세서리",
                    sectionProducts = listOf(
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/12/13/03/32/winter-1903225_1280.jpg",
                            productName = "[특가] 겨울 방한 목도리 5set",
                            saleAmount = 12_000
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/12/19/18/21/snowflake-1918794_1280.jpg",
                            productName = "겨울 방한 장갑",
                            saleAmount = 3_000
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2017/11/14/00/57/christmas-2947257_1280.jpg",
                            productName = "[나눔] 산타 할아버지의 선물",
                            saleAmount = 0
                        ),
                    )
                )
            }
            2 -> {
                SectionInfo(
                    sectionNo = 2,
                    sectionTitle = "겨울철 아우터",
                    sectionProducts = listOf(
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/01/08/06/13/woman-1127201_1280.jpg",
                            productName = "[특가] 사우스페이스 겨울 패딩",
                            saleAmount = 120_000
                        )
                    )
                )
            }
            3 -> {
                SectionInfo(
                    sectionNo = 3,
                    sectionTitle = "학용품",
                    sectionProducts = listOf(
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/06/29/08/50/pencils-1486278_1280.jpg",
                            productName = "[특가] 2023 신학기 연필 세트",
                            saleAmount = 5_500
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/01/02/08/01/correction-tape-1117994_1280.jpg",
                            productName = "모든걸 지우는 수정테이프",
                            saleAmount = 50_000
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2013/08/22/20/28/pencil-sharpener-174854_1280.jpg",
                            productName = "레트로 연필깎기",
                            saleAmount = 9_000
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2016/08/30/19/37/colored-pencils-1631833_1280.jpg",
                            productName = "세젤예 색연필 세트",
                            saleAmount = 12_000
                        )
                    )
                )
            }
            4 -> {
                SectionInfo(
                    sectionNo = 4,
                    sectionTitle = "신발",
                    sectionProducts = listOf(
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2014/09/03/20/15/shoes-434918_1280.jpg",
                            productName = "발냄새 절대 안나는 신발",
                            saleAmount = 39_800
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2017/05/25/15/08/jogging-2343558_1280.jpg",
                            productName = "헬스용 20kg 모래주머니 신발",
                            saleAmount = 30_000
                        ),
                        Product(
                            imageUrl = "https://cdn.pixabay.com/photo/2014/09/07/22/03/bubble-gum-438404_1280.jpg",
                            productName = "아스팔트 리무버",
                            saleAmount = 99_000
                        ),
                    )
                )
            }
            else -> SectionInfo(sectionTitle = "상품 없음 테스트")
        }
    }
}