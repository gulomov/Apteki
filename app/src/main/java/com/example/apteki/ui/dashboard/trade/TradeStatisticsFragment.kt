package com.example.apteki.ui.dashboard.trade

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentTradeStatisticsBinding
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.dialogForCalendar
import com.example.apteki.utils.toDpi
import java.util.*


class TradeStatisticsFragment : Fragment() {

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
        adapter = TradeStatisticsAdapter("Savdo")
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


}
