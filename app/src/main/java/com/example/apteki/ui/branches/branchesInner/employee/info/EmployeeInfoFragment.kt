package com.example.apteki.ui.branches.branchesInner.employee.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentBranchInfoBinding
import com.example.apteki.network.Resource
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.navigate
import com.example.apteki.utils.toDpi
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmployeeInfoFragment : Fragment() {

    private lateinit var adapter: EmployeeInfoAdapter
    private lateinit var _binding: FragmentBranchInfoBinding
    private val binding get() = _binding
    private var argumentsId = 0
    private val viewModel: EmployeeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
        argumentsId = arguments?.getInt("id")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBranchInfoBinding.inflate(inflater, container, false)
        setUpRecycler()
        setOnClickListener()
        getEmployee()
        return binding.root
    }

    private fun getEmployee() {
        viewModel.getEmployee(argumentsId)

        viewModel.resourceEmployee.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if (resource.data.success) adapter.update(resource.data.data)
                    }
                    is Resource.Error -> {
                    }
                    is Resource.GenericError -> {
                        Log.d(
                            "here",
                            "here2 $resource"
                        )
                    }
                }
            }
        })
    }

    private fun setUpAdapter() {
        adapter = EmployeeInfoAdapter()
    }

    private fun setUpRecycler() {
        binding.recyclerBranchInfo.adapter = adapter
        binding.recyclerBranchInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerBranchInfo.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))
    }

    private fun setOnClickListener() {
        binding.branchesAddBtn.setOnClickListener {
            navigate(R.id.nav_add_employee)
        }

    }
}