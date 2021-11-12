package com.example.apteki.data

import java.util.*
import kotlin.collections.ArrayList

class MockData {
    companion object {

        fun getBranchesData(): List<BranchesData> {
            val list = ArrayList<BranchesData>()
            list.add(BranchesData("Filial 1", "500.000", "so'm"))
            list.add(BranchesData("Filial 2", "5.000.000", "so'm"))
            list.add(BranchesData("Filial 3", "50.000.000", "so'm"))
            list.add(BranchesData("Filial 4", "550.000.000", "so'm"))
            return list
        }

        fun getTradeInfo(): List<TradesInfo> {
            val list = ArrayList<TradesInfo>()
            list.add(TradesInfo("15.10.2021", "1000.000", "1000.000", "1000.000"))
            list.add(TradesInfo("16.10.2021", "1000.000", "1000.000", "1000.000"))
            list.add(TradesInfo("17.10.2021", "1000.000", "1000.000", "1000.000"))
            list.add(TradesInfo("18.10.2021", "1000.000", "1000.000", "1000.000"))
            list.add(TradesInfo("19.10.2021", "1000.000", "1000.000", "1000.000"))

            return list
        }


    }

    data class BranchesData(
        val title: String = "",
        val amount: String = "",
        val currency: String = ""
    )

    data class TradesInfo(
        val date: String = "",
        val cash: String = "",
        val loan: String = "",
        val card: String = ""
    )
}