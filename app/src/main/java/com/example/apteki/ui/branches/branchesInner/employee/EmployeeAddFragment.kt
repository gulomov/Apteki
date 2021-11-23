package com.example.apteki.ui.branches.branchesInner.employee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import com.example.apteki.R
import com.example.apteki.databinding.FragmentEmployeeAddBinding
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.BranchesData
import com.example.apteki.ui.branches.branchesMain.BranchesViewModel
import com.example.apteki.utils.animDown
import com.example.apteki.utils.animUp
import com.example.apteki.utils.optionDone
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmployeeAddFragment : Fragment() {

    private lateinit var name: AppCompatEditText
    private lateinit var branchName: AppCompatEditText
    private lateinit var address: AppCompatEditText
    private lateinit var phone: AppCompatEditText
    private lateinit var userName: AppCompatEditText
    private lateinit var password: AppCompatEditText
    private lateinit var type: AppCompatEditText
    private var _binding: FragmentEmployeeAddBinding? = null
    private val viewModel: BranchesViewModel by viewModel()
    private val binding get() = _binding
    private var getBranches = ArrayList<BranchesData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeeAddBinding.inflate(inflater, container, false)
        setUpView()
        getBranches()
        setOnClickListener()
        return binding?.root
    }

    private fun setUpView() {
        name = binding!!.employeeNameEditText
        userName = binding!!.employeeUserNameEditText
        password = binding!!.passwordEditText
        type = binding!!.typeEditText
        branchName = binding!!.branchNameEditText
        address = binding!!.branchAddressEditText
        phone = binding!!.phoneNumberEditText
        optionDone(name)
        optionDone(userName)
        optionDone(password)
        optionDone(type)
        optionDone(branchName)
        optionDone(address)
        optionDone(phone)
    }

    private fun getBranches() {
        viewModel.getBranches()
        viewModel.resourceBranches.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        for (data in resource.data.data) {
                            getBranches.add(data)
                        }
                        getBranchesId(resource.data.data)

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

    private fun getBranchesId(data: ArrayList<BranchesData>) {
        Log.d("ides", " 3 $getBranches")
        Log.d("ides", " 4 $data")
    }

    private fun setOnClickListener() {
        binding?.addingEmployeeSubmitBtn?.setOnClickListener {
            if (name.text!!.isNotEmpty() && userName.text!!.isNotEmpty() && password.text!!.isNotEmpty() && type.text!!.isNotEmpty() && branchName.text!!.isNotEmpty() && address.text!!.isNotEmpty() && phone.text!!.isNotEmpty()) {

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