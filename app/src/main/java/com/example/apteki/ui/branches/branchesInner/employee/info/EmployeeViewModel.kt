package com.example.apteki.ui.branches.branchesInner.employee.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteki.network.Event
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.EmployeeResponse
import com.example.apteki.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EmployeeViewModel(private val repository: Repository) : ViewModel() {
    val resourceEmployee = MutableLiveData<Event<Resource<EmployeeResponse>>>()

    fun getEmployee(id: Int) {
        viewModelScope.launch {
            repository.getEmployee(id).onEach {
                resourceEmployee.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }
}