package com.example.apteki.ui.branches.branchesInner.employee.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentBranchInfoBinding
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.navigate
import com.example.apteki.utils.toDpi

class EmployeeInfoFragment : Fragment() {

    private lateinit var adapter: EmployeeInfoAdapter
    private var _binding: FragmentBranchInfoBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBranchInfoBinding.inflate(inflater, container, false)
        setUpRecycler()
        setOnClickListener()
        return binding?.root
    }

    private fun setUpAdapter() {
        adapter = EmployeeInfoAdapter()
    }

    private fun setUpRecycler() {
        binding?.recyclerBranchInfo?.adapter = adapter
        binding?.recyclerBranchInfo?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.recyclerBranchInfo?.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))
    }

    private fun setOnClickListener() {
        binding?.branchesAddBtn?.setOnClickListener {
           navigate(R.id.nav_add_employee)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}