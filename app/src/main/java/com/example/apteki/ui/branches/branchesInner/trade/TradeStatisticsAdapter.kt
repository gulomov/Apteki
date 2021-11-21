package com.example.apteki.ui.branches.branchesInner.trade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.R
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemTradeStatisticsAdapterBinding


class TradeStatisticsAdapter(type: String) :
    RecyclerView.Adapter<TradeStatisticsAdapter.ViewHolder>() {

    var type = type
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
            if (type == "Savdo") {
                binding.tradeDate.text = trade.date
                binding.cash.text = trade.cash
                binding.loan.text = trade.loan
                binding.card.text = trade.card
                binding.itemTitle.text = itemView.resources.getString(R.string.trade)
            } else if (type == "Fakturalar") {
                binding.itemTitle.text = itemView.resources.getString(R.string.invoices)
                binding.cashTitle.text =
                    itemView.resources.getString(R.string.invoice_warehouse_title)
                binding.cardTitle.visibility = View.GONE
                binding.card.visibility = View.GONE
                binding.loanTitle.visibility = View.GONE
                binding.loan.visibility = View.GONE
                binding.overallSumTitle.visibility = View.VISIBLE
                binding.overallSum.visibility = View.VISIBLE
                binding.tradeDate.text = trade.date
                binding.cash.text = trade.filialTitle
                binding.overallSum.text = trade.overallSum

            } else if (type == "Hodimlar") {
                binding.itemTitle.text = trade.employeeName
                binding.tradeDate.text = trade.date
                binding.cash.text = trade.cash
                binding.loan.text = trade.loan
                binding.card.text = trade.card
            }


        }

    }


}