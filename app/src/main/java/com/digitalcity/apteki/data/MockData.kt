package com.digitalcity.apteki.data

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