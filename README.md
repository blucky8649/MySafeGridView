# MySafeGridView

멀티 뷰 타입 그리드뷰에서 각 아이템별 섹션 헤더를 추가하고도 뷰를 재활용하고 싶은 욕심쟁이가 만든 예제
<div>
<img src="https://user-images.githubusercontent.com/83625797/205551754-8bdf770e-9ec4-40d8-b747-29900b937adc.png" width="300"/>
</div>


## 뷰 타입 지정
```kotlin
/**
 * @param dataString: 넣고자하는 데이터를 Json으로 변환하여 저장합니다. (어댑터에서 다시 역직렬화하는방식)
 * @param viewType: ViewType별 고유 번호를 지정합니다.
 **/
data class ShopViewType(
    val dataString: String = "",
    val viewType: Int = 0
)
```

## 뷰 타입 리스트 생성
```kotlin
fun calcGridData() : List<ShopViewType>{
    val viewTypeList: ArrayList<ShopViewType> = ArrayList()
    // 1. 섹션들의 고유 번호를 불러옵니다.
    getSectionNos().forEach { sectionNo ->
        // 2. 각 섹션 번호에 맞는 상품리스트들을 불러옵니다.
        getProductBySectionNo(sectionNo).also { sectionInfo ->
            // 3. 섹션 타이틀 뷰타입을 리스트에 넣습니다.
            viewTypeList.add(ShopViewType(sectionInfo.sectionTitle, 0))
            // 4. 섹션 상품 뷰타입들을 (Json으로 변환하여)리스트에 넣습니다.
            sectionInfo.sectionProducts.forEach { product ->
                viewTypeList.add(ShopViewType(Gson().toJson(product), 1))
            }
            // 4-1. 만약 섹션 상품의 수가 홀수면 빈값을 하나 더 채워넣습니다.
            if (sectionInfo.sectionProducts.size % 2 == 1) {
                viewTypeList.add(ShopViewType(Gson().toJson(Product()), 1))
            }
        }
    }
    return viewTypeList
}
```

## 어댑터 작성
```kotlin
class ShopAdapter(
    val mLayoutManager: GridLayoutManager
): ListAdapter<ShopViewType, RecyclerView.ViewHolder>(diffCallback) {
    companion object {
        val diffCallback= object : DiffUtil.ItemCallback<ShopViewType>() {
            override fun areItemsTheSame(oldItem: ShopViewType, newItem: ShopViewType): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: ShopViewType, newItem: ShopViewType): Boolean {
                return oldItem == newItem
            }
        }

        const val VT_TITLE = 0
        const val VT_PRODUCT = 1
    }
    
    // currentList가 변경될 때마다 SpanSize를 재조정함 <- POINT
    override fun onCurrentListChanged(
        previousList: MutableList<ShopViewType>,
        currentList: MutableList<ShopViewType>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(getItemViewType(position)) {
                    0 -> 2
                    else -> 1
                }
            }
        }
    }
    // 뷰 타입 지정
    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
    
    // 뷰가 재활용 되고 있는지 확인하기 위해 작성
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        Log.d("Adapter", "onViewRecycled called : ${holder.isRecyclable}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("Adapter", "onCreateViewHolder Called ! $viewType")
        return when(viewType) {
            VT_TITLE -> {
                SectionTitleViewHolder(
                    ItemSectionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                ProductViewHolder(
                    ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionTitleViewHolder -> {
                holder.bind(getItem(position).dataString)
            }
            is ProductViewHolder -> {
                // 데이터 역직렬화
                val product = Gson().fromJson(getItem(position).dataString, Product::class.java)
                holder.bind(product)
            }
        }
    }
}

class SectionTitleViewHolder(
    private val binding: ItemSectionTitleBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(title: String) {
        binding.textView.text = title
    }
}

class ProductViewHolder(
    private val binding: ItemProductBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.tvTitle.text = product.productName
        binding.tvPrice.text = if (product.productName.isNotEmpty()) "${product.saleAmount}원" else ""
        Glide.with(binding.root)
            .load(product.imageUrl)
            .centerCrop()
            .into(binding.imageView)
    }
}
```

## 결과
재활용 되긴한다.
<div>
<img src="https://user-images.githubusercontent.com/83625797/205552711-181317e9-9827-44d6-b46a-9747dd01e227.png" width="300"/>
</div>
