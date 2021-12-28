package com.digitalcity.apteki.ui.branches.branchesMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.digitalcity.apteki.databinding.ItemBranchesAdapterBinding
import com.digitalcity.apteki.network.pojo.BranchesData
import com.digitalcity.apteki.utils.format


class BranchesAdapter() : RecyclerView.Adapter<BranchesAdapter.ViewHolder>() {

    var branchesList = mutableListOf<BranchesData>()
    var onItemClick: ((BranchesData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBranchesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    fun update(branchesList: List<BranchesData>) {
        this.branchesList = branchesList as MutableList<BranchesData>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(branchesList!![position])
    }

    override fun getItemCount() = branchesList?.size ?: 0

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(branchesList!![adapterPosition])
            }
        }

        fun bindData(branches: BranchesData) {
            val binding = binding as ItemBranchesAdapterBinding
            binding.itemTitle.text = branches.name
            binding.itemAmount.text = branches.summa.toString()
            itemView.context.format(binding.itemAmount)
            binding.itemAmount.text.toString().replace(",", ".")
        }

    }
}

