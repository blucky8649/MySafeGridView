package com.example.mysafegridview.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysafegridview.data.model.Product
import com.example.mysafegridview.databinding.ItemProductBinding
import com.example.mysafegridview.databinding.ItemSectionTitleBinding
import com.example.mysafegridview.presentation.model.ShopViewType
import com.google.gson.Gson

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

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

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