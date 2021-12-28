package com.digitalcity.apteki.ui.branches.branchesMain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalcity.apteki.R
import com.digitalcity.apteki.databinding.FragmentBranchesBinding
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.BranchesData
import com.digitalcity.apteki.utils.SpacesItemDecoration
import com.digitalcity.apteki.utils.navigate
import com.digitalcity.apteki.utils.toDpi
import org.koin.androidx.viewmodel.ext.android.viewModel

class BranchesFragment : Fragment() {

    private var _binding: FragmentBranchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var branchesAdapter: BranchesAdapter
    private var brancheId = ""
    private val viewModel: BranchesViewModel by viewModel()
    private lateinit var branchData: BranchesData

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpAdapter()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBranchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpRecyclerView()
        addNewBranchClicking()
        getBranches()
        recyclerViewClick()
        return root
    }

    private fun setUpAdapter() {
        branchesAdapter = BranchesAdapter()
    }

    private fun setUpRecyclerView() {
        binding.branchesRecycler.adapter = branchesAdapter
        binding.branchesRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.branchesRecycler.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 2))
    }

    private fun addNewBranchClicking() {
        binding.branchesAddBtn.setOnClickListener {
            navigate(R.id.action_nav_branches_to_nav_newBranches)
        }
    }

    private fun recyclerViewClick() {
        branchesAdapter.onItemClick = {
            val bundle = Bundle()
            bundle.putInt("id", it.id)
            navigate(R.id.action_nav_branches_to_nav_branches_info, bundle)
        }
    }


    private fun getBranches() {
        viewModel.getBranches()
        viewModel.resourceBranches.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loader.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loader.visibility = View.GONE

                        if (resource.data.success) branchesAdapter.update(resource.data.data)
                    }
                    is Resource.Error -> {
                        binding.loader.visibility = View.GONE
                        Log.d(
                            "here",
                            "here2 ${resource.exception.message}"
                        )
                    }
                    is Resource.GenericError -> {
                        binding.loader.visibility = View.GONE

                        Log.d(
                            "here",
                            "here2 $resource"
                        )
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}