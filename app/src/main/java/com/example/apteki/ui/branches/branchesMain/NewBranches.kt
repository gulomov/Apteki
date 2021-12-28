package com.example.apteki.ui.branches.branchesMain

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apteki.R
import com.example.apteki.databinding.FragmentNewBranchesBinding
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.AddBranchRequest
import com.example.apteki.network.pojo.RegionsData
import com.example.apteki.ui.map.MapArguments
import com.example.apteki.ui.map.SharedViewModel
import com.example.apteki.utils.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewBranches : Fragment() {

    private var _binding: FragmentNewBranchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkBoxImg: ImageView
    private var check = false
    private var checkForRequest = "False"
    private val sharedViewModel: SharedViewModel by inject()
    private var args: MapArguments? = null
    private var location_start = ""
    private var location_end = ""
    private val viewModel: BranchesViewModel by viewModel()
    private lateinit var name: AppCompatEditText
    private lateinit var phone: AppCompatEditText
    private lateinit var address: AppCompatEditText
    private lateinit var workTime: AppCompatEditText
    private lateinit var region: TextView
    private lateinit var type: TextView
    private lateinit var adapter: RegionsAdapter
    private lateinit var typeAdapter: TypeAdapter
    private var regionId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBranchesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        checkBoxImg = binding.addingBranchDeliveryCheckBox
        setUpView()
        checkBoxClicking()
        setAddressClick()
        setLocation()
        getRegions()
        setUpRecyclerView()
        regionsClick()
        typeClick()
        goNext()
        return root
    }

    private fun setUpAdapter() {
        adapter = RegionsAdapter()
        typeAdapter = TypeAdapter()
    }

    private fun setUpRecyclerView() {
        binding.regionRecycler.adapter = adapter
        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))

        binding.typeRecycler.adapter = typeAdapter
        binding.typeRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.typeRecycler.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))


    }

    private fun setAddressClick() {
        binding.addingBranchLatitudeEditView.setOnClickListener {
            sharedViewModel.startedAddingMarket = true
            sharedViewModel.continueEditing = true
            navigate(R.id.action_nav_newBranches_to_mapFragment)
        }
    }

    private fun setUpView() {
        name = binding.addingBranchNameEditText
        phone = binding.addingBranchPhoneEditText
        address = binding.addingBranchAddressEditText
        workTime = binding.addingBranchWorkTimeEditText
        region = binding.addBranchRegion
        type = binding.addBranchType
        optionDone(name)
        optionDone(phone)
        optionDone(workTime)
        optionDone(address)
    }

    private fun setLocation() {
        if (sharedViewModel.startedAddingMarket && sharedViewModel.continueEditing) {
            sharedViewModel.mapArguments.observe(viewLifecycleOwner, Observer {
                args = it
                var lat = args?.lat.toString()
                var lng = args?.lng.toString()
                val final = lat + " va " + lng
                if (final.isNotEmpty())
                    binding.addingBranchLatitudeEditView.text =
                        resources.getString(R.string.address_detected)
                location_start = args?.lat.toString()
                location_end = args?.lng.toString()
            })
        }


    }

    private fun regionsClick() {
        var check = false
        binding.addBranchRegion.setOnClickListener {

            if (!check) {
                binding.regionRecycler.visibility = View.VISIBLE
                adapter.onItemClick = {
                    region.text = it.name
                    regionId = it.id
                    binding.regionRecycler.visibility = View.GONE
                    check = true
                }
                check = true
            } else {
                binding.regionRecycler.visibility = View.GONE
                check = false
            }
        }
    }

    private fun typeClick() {
        var check = false

        binding.addBranchType.setOnClickListener {
            if (!check) {
                binding.typeRecycler.visibility = View.VISIBLE
                typeAdapter.onItemClick = {
                    type.text = it.name
                    binding.typeRecycler.visibility = View.GONE
                    check = false
                }
                check = true
            } else {
                binding.typeRecycler.visibility = View.GONE
                check = false
            }
        }
    }

    private fun goNext() {
        binding.addingBranchSubmitBtn.setOnClickListener {

            if (name.text?.isNotEmpty()!! && phone.text?.isNotEmpty()!! &&
                address.text?.isNotEmpty()!! && workTime.text?.isNotEmpty()!! &&
                region.text.isNotEmpty() && type.text.isNotEmpty() && address.text!!.isNotEmpty() &&
                location_start.isNotEmpty() && location_end.isNotEmpty()
            ) {

                viewModel.addBranch(
                    AddBranchRequest(
                        regionId.toString(),
                        name.text.toString(),
                        phone.text.toString(),
                        address.text.toString(),
                        workTime.text.toString(),
                        checkForRequest,
                        location_start,
                        location_end,
                        type.text.toString()
                    )
                )
                Log.d(
                    "gee", "${
                        AddBranchRequest(
                            regionId.toString(),
                            name.text.toString(),
                            phone.text.toString(),
                            address.text.toString(),
                            workTime.text.toString(),
                            checkForRequest,
                            location_start,
                            location_end,
                            type.text.toString()
                        )
                    }"
                )
            } else {
                binding.toastText.text = resources.getString(R.string.fill_the_blank)
                animUp(binding.toastText)
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        animDown(binding.toastText)
                    }, 1000
                )
            }
        }
        viewModel.resourceNewBranch.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.nested.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progress.visibility = View.GONE
                        binding.nested.visibility = View.VISIBLE
                        if (resource.data.success) {

                            binding.toastText.text = resources.getString(R.string.new_branch_added)
                            animUp(binding!!.toastText)
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    animDown(binding.toastText)
                                    findNavController().popBackStack()
                                }, 1000
                            )

                        } else {
                            binding.toastText.text = resources.getString(R.string.fill_the_blank)
                            animUp(binding.toastText)
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    animDown(binding.toastText)
                                }, 1000
                            )
                        }
                    }
                    is Resource.Error -> {
                        binding.progress.visibility = View.GONE
                        binding.nested.visibility = View.VISIBLE
                        Log.d(
                            "gee",
                            "here2 ${resource.exception.message}"
                        )
                    }
                    is Resource.GenericError -> {
                        binding.progress.visibility = View.GONE
                        binding.nested.visibility = View.VISIBLE
                        Log.d(
                            "here",
                            "here2 $resource"
                        )
                    }
                }
            }
        })
    }


    private fun getRegions() {
        viewModel.getRegions()
        viewModel.resourceRegions.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (resource.data.success) {
                            adapter.update(resource.data.data)

                        }
                    }
                    is Resource.Error -> {
                        Log.d(
                            "gee",
                            "here2 ${resource.exception.message}"
                        )
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

    private fun checkBoxClicking() {
        checkBoxImg.setOnClickListener {
            if (!check) {
                checkBoxImg.setImageResource(R.drawable.checked)
                check = true
                checkForRequest = "True"
            } else {
                check = false
                checkForRequest = "False"
                checkBoxImg.setImageResource(R.drawable.un_checked)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}