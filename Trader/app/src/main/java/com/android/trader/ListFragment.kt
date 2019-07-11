package com.android.trader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL


class ListFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.list_fragment, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.product_list)
        val adapter = CustomAdapter(getProducts())
        recyclerView.adapter = adapter
        val decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)
        return rootView
    }

    private fun getProducts(): List<Product> {
        return listOf(
            Product("EUR/GBR", 0.89199, 0.89212, 0.15, true),
            Product("USD/CAD", 1.29059, 1.29072, -0.57, true),
            Product("EUR/USD", 1.23938, 1.23944, 0.47, true),
            Product("BTC/USD(Kraken)", 10_935.85, 10_985.85, -3.96, true),
            Product("Gold", 1_331.38, 1_331.63, 0.88, false),
            Product("USD/CAD", 1.29059, 1.29072, -0.57, true),
            Product("EUR/USD", 1.23938, 1.23944, -0.47, true),
            Product("EUR/GBR", 0.89199, 0.89212, 0.15, true),
            Product("USD/CAD", 1.29059, 1.29072, -0.57, true),
            Product("EUR/USD", 1.23938, 1.23944, 0.47, true),
            Product("BTC/USD(Kraken)", 10_935.85, 10_985.85, -3.96, true),
            Product("Gold", 1_331.38, 1_331.63, 0.88, false),
            Product("USD/CAD", 1.29059, 1.29072, -0.57, true),
            Product("EUR/USD", 1.23938, 1.23944, -0.47, true)
        )
    }
}
