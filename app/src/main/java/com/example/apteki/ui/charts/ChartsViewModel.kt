package com.example.apteki.ui.charts

import android.app.usage.UsageEvents
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteki.network.Event
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.ChartsResponse
import com.example.apteki.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChartsViewModel(private val repository: Repository) : ViewModel() {
    val resourceCharts = MutableLiveData<Event<Resource<ChartsResponse>>>()

    fun getCharts() {
        viewModelScope.launch {
            repository.getCharts().onEach {
                resourceCharts.value = Event(it)
            }.launchIn(viewModelScope)
        }
    }
}