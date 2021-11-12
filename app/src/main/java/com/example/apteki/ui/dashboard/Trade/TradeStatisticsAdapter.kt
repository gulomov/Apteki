package com.example.apteki.ui.dashboard.Trade

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.R
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemTradeStatisticsAdapterBinding


class TradeStatisticsAdapter : RecyclerView.Adapter<TradeStatisticsAdapter.ViewHolder>() {

    var tradeStatsList: List<MockData.TradesInfo>? = MockData.getTradeInfo()
    var onItemClick: ((MockData.TradesInfo) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradeStatisticsAdapter.ViewHolder {
        val binding =
            ItemTradeStatisticsAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TradeStatisticsAdapter.ViewHolder, position: Int) {
        holder.bindData(tradeStatsList!![position])


    }

    override fun getItemCount() = tradeStatsList?.size ?: 0

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(tradeStatsList!![adapterPosition])
            }
        }

        fun bindData(trade: MockData.TradesInfo) {
            val binding = binding as ItemTradeStatisticsAdapterBinding

            binding.tradeDate.text = trade.date
            binding.cash.text = trade.cash
            binding.loan.text = trade.loan
            binding.card.text = trade.card
        }

    }


}