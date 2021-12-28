package com.digitalcity.apteki.ui.branches.branchesInner.invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.digitalcity.apteki.databinding.ItemInvoiceStatisticsBinding
import com.digitalcity.apteki.network.pojo.InvoiceResult
import com.digitalcity.apteki.utils.format

class InvoiceAdapter() :
    PagingDataAdapter<InvoiceResult, InvoiceAdapter.ViewHolder>(ORDER_COMPARATOR) {

    var onItemClick: ((InvoiceResult) -> Unit)? = null

    override fun onBindViewHolder(holder: InvoiceAdapter.ViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemInvoiceStatisticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position)!!)
            }
        }

        fun bindData(invoice: InvoiceResult) {
            val binding = binding as ItemInvoiceStatisticsBinding
            binding.tradeDate.text = invoice.date
            binding.branch.text = invoice.warehouse.name
            binding.money.text = invoice.summa.toString()
            itemView.context.format(binding.money)
        }

    }

    companion object {
        val ORDER_COMPARATOR = object : DiffUtil.ItemCallback<InvoiceResult>() {
            override fun areItemsTheSame(
                oldItem: InvoiceResult,
                newItem: InvoiceResult
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: InvoiceResult,
                newItem: InvoiceResult
            ): Boolean =
                oldItem == newItem
        }
    }


}