package com.digitalcity.apteki.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {
    var mapArguments = MutableLiveData<MapArguments>()
    var firstUse:Boolean =true
    var continueEditing:Boolean =false
    var startedAddingMarket:Boolean=false
    var finishedAddingMarket:Boolean=false
}

data class MapArguments(
    var lat: Double,
    var lng: Double
)
