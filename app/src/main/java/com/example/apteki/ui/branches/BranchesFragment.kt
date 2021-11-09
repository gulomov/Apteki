package com.example.apteki.ui.branches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentBranchesBinding
import com.example.apteki.utils.SpacesItemDecoration
import com.example.apteki.utils.navigate
import com.example.apteki.utils.toDpi

class BranchesFragment : Fragment() {

    private var _binding: FragmentBranchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var branchesAdapter: BranchesAdapter


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}