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
            list.add(
                TradesInfo(
                    "15.10.2021",
                    "1000.000",
                    "1000.000",
                    "1000.000",
                    "Fillial 1",
                    "1000.000.000",
                    "Jahogir"
                )
            )
            list.add(
                TradesInfo(
                    "16.10.2021",
                    "1000.000",
                    "1000.000",
                    "1000.000",
                    "Fillial 2",
                    "1000.000.000",
                    "Jahogir"

                )
            )
            list.add(
                TradesInfo(
                    "17.10.2021",
                    "1000.000",
                    "1000.000",
                    "1000.000",
                    "Fillial 3",
                    "1000.000.000",
                    "Jahogir"

                )
            )
            list.add(
                TradesInfo(
                    "18.10.2021",
                    "1000.000",
                    "1000.000",
                    "1000.000",
                    "Fillial 4",
                    "1000.000.000",
                    "Jahogir"

                )
            )
            list.add(
                TradesInfo(
                    "19.10.2021",
                    "1000.000",
                    "1000.000",
                    "1000.000",
                    "Fillial 5",
                    "1000.000.000",
                    "Jahogir"

                )
            )

            return list
        }

        fun getBranchInfo(): List<BranchInfo> {
            val list = ArrayList<BranchInfo>()
            list.add(BranchInfo("Johongir", "Andijon Shaxar", "90 123-45-67", "Fillial 1"))
            list.add(BranchInfo("Johongir", "Andijon Shaxar", "90 123-45-67", "Fillial 1"))
            list.add(BranchInfo("Johongir", "Andijon Shaxar", "90 123-45-67", "Fillial 1"))
            return list
        }

    }

    data class BranchesData(
        val title: String = "",
        val amount: String = "",
        val currency: String = ""
    )

    data class TradesInfo(
        val date: String? = null,
        val cash: String? = null,
        val loan: String? = null,
        val card: String? = null,
        val filialTitle: String? = null,
        val overallSum: String? = null,
        val employeeName: String? = null,
    )

    data class BranchInfo(
        val name: String,
        val address: String,
        val phone: String,
        val branch: String
    )
}