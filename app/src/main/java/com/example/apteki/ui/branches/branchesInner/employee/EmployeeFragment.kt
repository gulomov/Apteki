package com.example.apteki.ui.branches.branchesInner.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.databinding.FragmentTradeStatisticsBinding
import com.example.apteki.ui.branches.branchesInner.trade.TradeStatisticsAdapter
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.dialogForCalendar
import com.example.apteki.utils.toDpi

class EmployeeFragment : Fragment() {
    private var _binding: FragmentTradeStatisticsBinding? = null
    private lateinit var adapter: TradeStatisticsAdapter
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTradeStatisticsBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setUpDatePickerClick()
        return binding?.root
    }

    private fun setUpAdapter() {
        adapter = TradeStatisticsAdapter("Hodimlar")
    }

    private fun setUpRecyclerView() {
        binding?.dashboardTradeRecycler?.adapter = adapter
        binding?.dashboardTradeRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.dashboardTradeRecycler?.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))

    }

    private fun setUpDatePickerClick() {
        binding?.fromData?.setOnClickListener {
            dialogForCalendar("fromData", binding?.fromData!!)
        }
        binding?.toData?.setOnClickListener {
            dialogForCalendar("toData", binding?.toData!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}