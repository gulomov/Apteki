package com.digitalcity.apteki.ui.branches.branchesInner.employee

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcity.apteki.network.Event
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.AddEmployeeRequest
import com.digitalcity.apteki.network.pojo.AddEmployeeResponse
import com.digitalcity.apteki.network.pojo.EmployeeResponse
import com.digitalcity.apteki.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EmployeeViewModel(private val repository: Repository) : ViewModel() {
    val resourceEmployee = MutableLiveData<Event<Resource<EmployeeResponse>>>()
    val addResourceEmployee = MutableLiveData<Event<Resource<AddEmployeeResponse>>>()

    fun getEmployee(id: Int) {
        viewModelScope.launch {
            repository.getEmployee(id).onEach {
                resourceEmployee.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }

    fun addEmployee(
        addEmployeeRequest: AddEmployeeRequest
    ) {
        viewModelScope.launch {
            repository.addEmployee(addEmployeeRequest)
                .onEach {
                    addResourceEmployee.value = Event(it)
                }.launchIn(viewModelScope)
        }
    }
}