package com.example.apteki.data

import java.util.*
import kotlin.collections.ArrayList

class MockData {
    companion object {

        fun getBranchesData(): List<BranchesData> {
            var list = ArrayList<BranchesData>()
            list.add(BranchesData("Filial 1", "500.000", "so'm"))
            list.add(BranchesData("Filial 2", "5.000.000", "so'm"))
            list.add(BranchesData("Filial 3", "50.000.000", "so'm"))
            list.add(BranchesData("Filial 4", "550.000.000", "so'm"))
            return list
        }
    }

    data class BranchesData(
        val title: String = "",
        val amount: String = "",
        val currency: String = ""
    )
}