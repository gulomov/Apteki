package com.example.apteki.ui.dashboard.Trade

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentTradeStatisticsBinding
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.toDpi


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
        return binding?.root
    }

    private fun setUpAdapter() {
        adapter = TradeStatisticsAdapter()
    }

    /*TODO FINISH THE DATE PICKED*/
    private fun setUpRecyclerView() {
        binding?.dashboardTradeRecycler?.adapter = adapter
        binding?.dashboardTradeRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.dashboardTradeRecycler?.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))
    }
}