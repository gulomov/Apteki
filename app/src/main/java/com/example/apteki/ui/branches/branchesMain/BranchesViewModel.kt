package com.example.apteki.ui.branches.branchesMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteki.network.Event
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.*
import com.example.apteki.repository.Repository
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