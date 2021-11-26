package com.example.apteki.ui.branches.branchesInner.employee.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.apteki.data.MockData
import com.example.apteki.databinding.ItemBranchInfoBinding
import com.example.apteki.network.pojo.BranchesData
import com.example.apteki.network.pojo.EmployeeData

class EmployeeInfoAdapter : RecyclerView.Adapter<EmployeeInfoAdapter.ViewHolder>() {


    var tradeStatsList = mutableListOf<EmployeeData>()
    var onItemClick: ((EmployeeData) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeInfoAdapter.ViewHolder {
        val binding =
            ItemBranchInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    fun update(tradeStatsList: List<EmployeeData>) {
        this.tradeStatsList = tradeStatsList as MutableList<EmployeeData>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EmployeeInfoAdapter.ViewHolder, position: Int) {
        holder.bindData(tradeStatsList[position])


    }

    override fun getItemCount() = tradeStatsList.size ?: 0

    inner class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(tradeStatsList[adapterPosition])
            }
        }

        fun bindData(info: EmployeeData) {
            val binding = binding as ItemBranchInfoBinding
            binding.title.text = info.full_name
            binding.addressValue.text = info.address
            binding.phoneValue.text = info.phone
            binding.branchNumberValue.text = info.branch?.name
        }

    }


}
