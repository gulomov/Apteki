package com.example.apteki.data

import com.example.apteki.network.pojo.RegionsData
import com.github.mikephil.charting.data.PieEntry
import java.util.*
import kotlin.collections.ArrayList

class MockData {
    companion object {
        fun getType(): ArrayList<Type> {
            val list = ArrayList<Type>()
            list.add(Type("Filial"))
            list.add(Type("Ombor"))

            return list
        }


    }

    data class Type(val name: String)
}