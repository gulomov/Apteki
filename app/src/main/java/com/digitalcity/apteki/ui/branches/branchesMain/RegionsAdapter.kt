package com.digitalcity.apteki.ui.branches.branchesMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.digitalcity.apteki.databinding.ItemBranchSpinerBinding
import com.digitalcity.apteki.network.pojo.RegionsData

class RegionsAdapter() : RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {
    var regionsList = mutableListOf<RegionsData>()
    var onItemClick: ((RegionsData) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegionsAdapter.ViewHolder {
        val binding =
            ItemBranchSpinerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    fun update(regionsList: List<RegionsData>) {
        this.regionsList = regionsList as MutableList<RegionsData>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RegionsAdapter.ViewHolder, position: Int) {
        holder.bindData(regionsList[position])
    }

    override fun getItemCount() = regionsList.size ?: 0


    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(regionsList[adapterPosition])
            }
        }

        fun bindData(branches: RegionsData) {
            val binding = binding as ItemBranchSpinerBinding
            binding.branchName.text = branches.name.toString()
        }

    }
}