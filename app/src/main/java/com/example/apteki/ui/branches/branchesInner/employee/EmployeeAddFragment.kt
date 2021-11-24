package com.example.apteki.ui.branches.branchesInner.employee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.Observer
import com.example.apteki.R
import com.example.apteki.databinding.FragmentEmployeeAddBinding
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.BranchesData
import com.example.apteki.ui.branches.branchesMain.BranchesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apteki.network.pojo.AddEmployeeRequest
import com.example.apteki.utils.*
import kotlinx.coroutines.NonDisposableHandle.parent


class EmployeeAddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var name: AppCompatEditText
    private lateinit var address: AppCompatEditText
    private lateinit var phone: AppCompatEditText
    private lateinit var userName: AppCompatEditText
    private lateinit var password: AppCompatEditText
    private lateinit var typeSpinner: Spinner
    private lateinit var branchRecycler: RecyclerView
    private lateinit var branchText: AppCompatTextView
    private lateinit var adapter: EmployeeBranchesAdapter
    private var type: String = ""
    private var branch: String = ""
    private var _binding: FragmentEmployeeAddBinding? = null
    private val viewModel: BranchesViewModel by viewModel()
    private val employeeViewModel: EmployeeViewModel by viewModel()
    private val binding get() = _binding
    private var getBranches = ArrayList<BranchesData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeeAddBinding.inflate(inflater, container, false)
        setUpView()
        setUpTypeSpinner()
        setOnClickListener()
        getBranches()
        setUpRecyclerView()
        setUpBranchesClick()
        getResponse()
        return binding?.root
    }

    private fun setUpView() {
        name = binding!!.employeeNameEditText
        userName = binding!!.employeeUserNameEditText
        password = binding!!.passwordEditText
        typeSpinner = binding!!.typeSpinner
        branchText = binding!!.branchSpinner
        branchRecycler = binding!!.branchRecycler
        address = binding!!.branchAddressEditText
        phone = binding!!.phoneNumberEditText
        optionDone(name)
        optionDone(userName)
        optionDone(password)
        optionDone(address)
        optionDone(phone)
    }

    private fun setUpAdapter() {
        adapter = EmployeeBranchesAdapter()
    }

    private fun setUpRecyclerView() {
        branchRecycler.adapter = adapter
        branchRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        branchRecycler.addItemDecoration(SpacesItemDecoration(toDpi(16), true, 0))
    }

    private fun setUpTypeSpinner() {
        typeSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type_array,
            android.R.layout.simple_spinner_item,
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_add_employee)
            typeSpinner.adapter = adapter
        }
    }

    private fun getBranches() {
        viewModel.getBranches()
        viewModel.resourceBranches.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (resource.data.success) adapter.update(resource.data.data)
                    }
                    is Resource.Error -> {
                        Log.d("er", "${resource.exception.message}")
                    }
                    is Resource.GenericError -> {
                        Log.d("er", resource.errorResponse.message)
                    }
                }
            }
        })
    }

    private fun setOnClickListener() {
        binding?.addingEmployeeSubmitBtn?.setOnClickListener {
            if (name.text!!.isNotEmpty() && userName.text!!.isNotEmpty() && password.text!!.isNotEmpty() && type.isNotEmpty() && branchText.text.isNotEmpty() && address.text!!.isNotEmpty() && phone.text!!.isNotEmpty()) {

                employeeViewModel.addEmployee(
                    AddEmployeeRequest(
                        name.text.toString(),
                        userName.text.toString(),
                        password.text.toString(),
                        type,
                        phone.text.toString(),
                        address.text.toString(),
                        branch,
                    )
                )
                Log.d(
                    "here",
                    "clicked ${
                        AddEmployeeRequest(
                            name.text.toString(),
                            userName.text.toString(),
                            password.text.toString(),
                            type,
                            phone.text.toString(),
                            address.text.toString(),
                            branch,
                        )
                    }"
                )
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

    private fun getResponse() {
        employeeViewModel.addResourceEmployee.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(
                            "here",
                            "here23"
                        )
                    }
                    is Resource.Success -> {
                        Log.d(
                            "here",
                            "here21 $resource"
                        )
                    }
                    is Resource.Error -> {
                        Log.d(
                            "here",
                            "here34 "
                        )
                    }
                    is Resource.GenericError -> {
                        Log.d(
                            "here",
                            "here2 "
                        )
                    }
                }
            }
        })
    }

    private fun setUpBranchesClick() {
        var check = false
        branchText.setOnClickListener {
            if (!check) {
                branchRecycler.visibility = View.VISIBLE
                adapter.onItemClick = {
                    branchText.text = it.name
                    branch = it.id.toString()
                    branchRecycler.visibility = View.GONE
                    check = false
                }
                check = true
            } else {
                branchRecycler.visibility = View.GONE
                check = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        type = typeSpinner.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}