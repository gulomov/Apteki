package com.example.apteki.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apteki.R
import com.example.apteki.databinding.FragmentDashboardBinding
import com.example.apteki.utils.navigate

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        setUpClickListener()
        return binding.root
    }

    private fun setUpClickListener() {
        binding.dashboardTradeBtn.setOnClickListener {
            navigate(R.id.action_nav_dashboard_to_nav_trade_stats)
        }
        binding.dashboardInvoicesBtn.setOnClickListener {
            navigate(R.id.action_nav_dashboard_to_invoiceFragment)
        }

        binding.dashboardEmployee.setOnClickListener {
            navigate(R.id.action_nav_dashboard_to_employeeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}