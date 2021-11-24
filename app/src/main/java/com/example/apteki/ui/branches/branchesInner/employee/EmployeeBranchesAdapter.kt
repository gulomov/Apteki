package com.example.apteki.ui.branches.branchesInner.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.databinding.ItemBranchSpinerBinding
import com.example.apteki.databinding.SimpleSpinnerAddEmployeeBinding
import com.example.apteki.network.pojo.BranchesData

class EmployeeBranchesAdapter() : RecyclerView.Adapter<EmployeeBranchesAdapter.ViewHolder>() {
    var branchesList = mutableListOf<BranchesData>()
    var onItemClick: ((BranchesData) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemBranchSpinerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    fun update(branchesList: ArrayList<BranchesData>) {
        this.branchesList = branchesList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(branchesList[position])
    }

    override fun getItemCount() = branchesList.size ?: 0

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(branchesList[adapterPosition])
            }
        }
        fun bindData(branches: BranchesData) {
            val binding = binding as ItemBranchSpinerBinding
            binding.branchName.text = branches.name.toString()
        }

    }


}