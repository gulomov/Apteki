package com.example.apteki.ui.branches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.R
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemBranchesAdapterBinding


class BranchesAdapter() : RecyclerView.Adapter<BranchesAdapter.ViewHolder>() {

    var branchesList: List<MockData.BranchesData>? = MockData.getBranchesData()
    var onItemClick: ((MockData.BranchesData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchesAdapter.ViewHolder {
        val binding =
            ItemBranchesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BranchesAdapter.ViewHolder, position: Int) {
        holder.bindData(branchesList!![position])
    }

    override fun getItemCount() = branchesList?.size ?: 0

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(branchesList!![adapterPosition])
            }
        }

        fun bindData(branches: MockData.BranchesData) {
            val binding = binding as ItemBranchesAdapterBinding
            binding.itemTitle.text = branches.title
            binding.itemAmount.text = branches.amount
            binding.itemCurrency.text = branches.currency
        }
    }

}