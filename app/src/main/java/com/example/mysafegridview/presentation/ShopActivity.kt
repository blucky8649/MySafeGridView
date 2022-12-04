package com.example.mysafegridview.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysafegridview.R
import com.example.mysafegridview.databinding.ActivityMainBinding
import com.example.mysafegridview.presentation.adapter.ShopAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var shopAdapter: ShopAdapter
//    private val shopAdapter: ShopAdapter by lazy {
//        ShopAdapter()
//}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val lm = GridLayoutManager(this@ShopActivity, 2)
//        lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                Log.d("UseCases", "position : $position")
//                return if(position == 0) 1 else 2
//            }
//        }
        shopAdapter = ShopAdapter(lm)
        binding.rvGrid.apply {
            layoutManager = lm
            adapter = shopAdapter
        }
        viewModel.calcGridData().apply {
            Log.d("UseCases", "List : $this")
            shopAdapter.submitList(this)
        }

    }
}