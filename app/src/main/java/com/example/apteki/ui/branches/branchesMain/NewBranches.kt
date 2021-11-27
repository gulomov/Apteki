package com.example.apteki.ui.branches.branchesMain

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apteki.R
import com.example.apteki.databinding.FragmentNewBranchesBinding
import com.example.apteki.ui.map.MapArguments
import com.example.apteki.ui.map.SharedViewModel
import com.example.apteki.utils.navigate
import org.koin.android.ext.android.inject


class NewBranches : Fragment() {

    private var _binding: FragmentNewBranchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkBoxImg: ImageView
    private var check = false
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private val sharedViewModel: SharedViewModel by inject()
    private var args: MapArguments? = null
    private var location_start = 41.2222
    private var location_end = 41.2222
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBranchesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        checkBoxImg = binding.addingBranchDeliveryCheckBox
        checkBoxClicking()
        setAddressClick()
        setLocation()
        return root
    }

    private fun setAddressClick() {
        binding.addingBranchLatitudeEditView.setOnClickListener {
            sharedViewModel.startedAddingMarket = true
            sharedViewModel.continueEditing = true
            navigate(R.id.action_nav_newBranches_to_mapFragment)
        }
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
                location_start = args?.lat!!
                location_end = args?.lng!!
                Log.d("address","$location_start and $location_end")
            })
        }


    }

    private fun checkBoxClicking() {
        checkBoxImg.setOnClickListener {
            if (!check) {
                checkBoxImg.setImageResource(R.drawable.checked)
                check = true
            } else {
                check = false
                checkBoxImg.setImageResource(R.drawable.un_checked)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}