package com.example.apteki.ui.branches.branchesMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemBranchSpinerBinding
import com.example.apteki.network.pojo.RegionsData

class TypeAdapter() : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {
    var typeList: ArrayList<MockData.Type> = MockData.getType()
    var onItemClick: ((MockData.Type) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeAdapter.ViewHolder {
        val binding =
            ItemBranchSpinerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeAdapter.ViewHolder, position: Int) {
        holder.bindData(typeList[position])
    }

    override fun getItemCount() = typeList.size

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(typeList[adapterPosition])
            }
        }

        fun bindData(type: MockData.Type) {
            val binding = binding as ItemBranchSpinerBinding
            binding.branchName.text = type.name
        }

    }
}