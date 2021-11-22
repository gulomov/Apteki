package com.example.apteki.ui.branches.branchesInner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apteki.R
import com.example.apteki.databinding.FragmentInnerBranchBinding
import com.example.apteki.utils.navigate

class BranchesInnerFragment : Fragment() {

    private var _binding: FragmentInnerBranchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInnerBranchBinding.inflate(inflater, container, false)

        setUpClickListener()
        getIds()
        return binding.root
    }

    private fun getIds() {
        Log.d("args", "${arguments?.getInt("id")}")
    }


    private fun setUpClickListener() {
        val bundle = Bundle()
        bundle.putInt("id", arguments?.getInt("id")!!)
        binding.tradeBtn.setOnClickListener {
            navigate(R.id.action_nav_branches_info_to_nav_trade_stats, bundle)
        }
        binding.invoicesBtn.setOnClickListener {
            navigate(R.id.action_nav_dashboard_to_invoiceFragment, bundle)
        }

        binding.employee.setOnClickListener {
            navigate(R.id.action_nav_branches_info_to_nav_employee_info, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}