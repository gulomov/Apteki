package com.example.apteki.ui.branches.branchesInner.trade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.R
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemTradeStatisticsAdapterBinding
import com.example.apteki.network.pojo.TradeResult
import com.example.apteki.utils.format


class TradeStatisticsAdapter() :
    PagingDataAdapter<TradeResult, TradeStatisticsAdapter.ViewHolder>(ORDER_COMPARATOR) {
    var onItemClick: ((TradeResult) -> Unit)? = null

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
    }


    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position)!!)
            }
        }

        fun bindData(trade: TradeResult) {
            val binding = binding as ItemTradeStatisticsAdapterBinding
            binding.tradeDate.text = trade.date
            binding.cash.text = trade.naqd.toString()
            binding.loan.text = trade.nasiya.toString()
            binding.card.text = trade.plastik.toString()
            binding.itemTitle.text = itemView.resources.getString(R.string.trade)
            itemView.context.format(binding.cash)
            itemView.context.format(binding.loan)
            itemView.context.format(binding.card)
        }
    }

    companion object {
        val ORDER_COMPARATOR = object : DiffUtil.ItemCallback<TradeResult>() {
            override fun areItemsTheSame(
                oldItem: TradeResult,
                newItem: TradeResult
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TradeResult,
                newItem: TradeResult
            ): Boolean =
                oldItem == newItem
        }
    }
}