package com.android.trader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Typeface
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CustomAdapter(private val data: List<Product>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val left: ConstraintLayout = itemView.findViewById(R.id.left)
        private val right: ConstraintLayout = itemView.findViewById(R.id.right)

        fun bind(product: Product){
            val formatter = DecimalFormat("#,###.#####", DecimalFormatSymbols.getInstance(Locale.ENGLISH))

            left.findViewById<TextView>(R.id.main).text = product.name
            val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
            left.findViewById<TextView>(R.id.main).typeface = boldTypeface
            left.findViewById<Button>(R.id.btn).text = itemView.context.resources.getText(R.string.btn_sell_text)
            left.findViewById<TextView>(R.id.price).text = formatter.format(product.sellPrice)

            right.findViewById<TextView>(R.id.main).text = String.format("%.2f%%", product.percentage)
            val percentageColor = if (product.percentage > 0) R.color.colorPositive else R.color.colorNegative
            right.findViewById<TextView>(R.id.main).setTextColor(ContextCompat.getColor(itemView.context, percentageColor))
            right.findViewById<Button>(R.id.btn).text = itemView.context.resources.getText(R.string.btn_buy_text)
            right.findViewById<TextView>(R.id.price).text = formatter.format(product.buyPrice)

            if (product.isCurrencyPair){
                left.findViewById<TextView>(R.id.price).setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrice))
                right.findViewById<TextView>(R.id.price).setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrice))
            }
        }
    }

}