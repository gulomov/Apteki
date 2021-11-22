package com.example.apteki.ui.branches.branchesInner.invoice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.databinding.FragmentTradeStatisticsBinding
import com.example.apteki.ui.branches.branchesInner.trade.TradeStatisticsAdapter
import com.example.apteki.ui.branches.branchesInner.trade.TradeViewModel
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.dialogForCalendar
import com.example.apteki.utils.toDpi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InvoiceFragment : Fragment() {
    private lateinit var _binding: FragmentTradeStatisticsBinding
    private lateinit var adapter: InvoiceAdapter
    private val binding get() = _binding
    private val viewModel: InvoiceViewModel by viewModel()
    private var argumentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
        argumentId = arguments?.getInt("id")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTradeStatisticsBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setUpDatePickerClick()
        getTodayInvoice()
        getFilteredData()
        return binding.root
    }

    private fun setUpAdapter() {
        adapter = InvoiceAdapter()
    }

    private fun setUpRecyclerView() {
        binding.tradeRecycler.adapter = adapter
        binding.tradeRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.tradeRecycler.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))

    }

    private fun setUpDatePickerClick() {
        binding.fromData.setOnClickListener {
            dialogForCalendar("fromData", binding.fromData)
        }
        binding.toData.setOnClickListener {
            dialogForCalendar("toData", binding.toData)
        }
    }

    private fun getTodayInvoice() {
        lifecycleScope.launch {
            viewModel.getTodaysInvoice(argumentId).collect { info ->
                Log.d("collect", "here")
                adapter.submitData(info)
                return@collect
            }
        }
    }

    private fun getFilteredData() {

        if (binding.fromData.text.toString().isNotEmpty() && binding.toData.text.toString()
                .isNotEmpty()
        ) {
            binding.filterBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getTodaysInvoiceByFilter(
                        argumentId,
                        "filter",
                        binding.fromData.text?.toString()!!,
                        binding.toData.text?.toString()!!
                    ).collect { info ->
                        Log.d("submitData", "2 clicking ")
                        adapter.submitData(info)
                        return@collect
                    }
                }
            }
        }
    }


}