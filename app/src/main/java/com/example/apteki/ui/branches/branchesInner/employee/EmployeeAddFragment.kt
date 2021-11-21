package com.example.apteki.ui.branches.branchesInner.employee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.example.apteki.R
import com.example.apteki.databinding.FragmentEmployeeAddBinding
import com.example.apteki.utils.animDown
import com.example.apteki.utils.animUp
import com.example.apteki.utils.optionDone

class EmployeeAddFragment : Fragment() {

    private lateinit var name: AppCompatEditText
    private lateinit var branchName: AppCompatEditText
    private lateinit var address: AppCompatEditText
    private lateinit var phone: AppCompatEditText
    private var _binding: FragmentEmployeeAddBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeeAddBinding.inflate(inflater, container, false)
        setUpView()
        setOnClickListener()
        return binding?.root
    }

    private fun setUpView() {
        name = binding!!.employeeNameEditText
        branchName = binding!!.branchNameEditText
        address = binding!!.branchAddressEditText
        phone = binding!!.phoneNumberEditText
        optionDone(name)
        optionDone(branchName)
        optionDone(address)
        optionDone(phone)
    }

    private fun setOnClickListener() {
        binding?.addingEmployeeSubmitBtn?.setOnClickListener {
            if (name.text!!.isNotEmpty() && branchName.text!!.isNotEmpty() && address.text!!.isNotEmpty() && phone.text!!.isNotEmpty()) {
                /*TODO connect navigation*/
            } else {
                binding!!.toastText.text = resources.getString(R.string.fill_the_blank)
                animUp(binding!!.toastText)
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        animDown(binding!!.toastText)
                    }, 1000
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}