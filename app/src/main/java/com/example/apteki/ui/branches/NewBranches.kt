package com.example.apteki.ui.branches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apteki.R
import com.example.apteki.databinding.FragmentNewBranchesBinding


class NewBranches : Fragment() {

    private var _binding: FragmentNewBranchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkBoxImg: ImageView
    private lateinit var drawerIcon: ImageView
    private var check = false
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //some logic that needs to be run before fragment is destroyed
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            onBackPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBranchesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        checkBoxImg = binding.addingBranchDeliveryCheckBox
        drawerIcon = activity?.findViewById<ImageView>(R.id.drawerIcon)!!
        checkBoxClicking()
        onBackPressed()
        return root
    }

    private fun onBackPressed() {
        drawerIcon.setOnClickListener {
            findNavController().popBackStack()
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