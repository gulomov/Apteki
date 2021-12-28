package com.digitalcity.apteki.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SearchProductResponse(
    val success: Boolean,
    val data: List<SearchProductData>
)

data class SearchProductData(
    val product_id: Int,
    @SerializedName("product__name")
    @Expose
    val product__name: String,
    val price: Int,
    val soni: Int

)