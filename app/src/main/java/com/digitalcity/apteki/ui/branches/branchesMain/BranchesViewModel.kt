package com.digitalcity.apteki.ui.branches.branchesMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalcity.apteki.network.Event
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.AddBranchRequest
import com.digitalcity.apteki.network.pojo.AddBranchResponse
import com.digitalcity.apteki.network.pojo.BranchesResponse
import com.digitalcity.apteki.network.pojo.RegionsResponse
import com.digitalcity.apteki.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BranchesViewModel(private val repository: Repository) : ViewModel() {
    val resourceBranches = MutableLiveData<Event<Resource<BranchesResponse>>>()
    val resourceRegions = MutableLiveData<Event<Resource<RegionsResponse>>>()
    val resourceNewBranch = MutableLiveData<Event<Resource<AddBranchResponse>>>()

    fun getBranches() {
        viewModelScope.launch {
            repository.getBranches().onEach {
                resourceBranches.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }

    fun getRegions() {
        viewModelScope.launch {
            repository.getRegions().onEach {
                resourceRegions.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }

    fun addBranch(branch: AddBranchRequest) {
        viewModelScope.launch {
            repository.addBranch(branch).onEach {
                resourceNewBranch.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }

}