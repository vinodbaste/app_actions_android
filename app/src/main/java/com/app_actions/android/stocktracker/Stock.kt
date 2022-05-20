
package com.app_actions.android.stocktracker

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Stock(
    @SerializedName("Global Quote")
    val stockData: StockData) : Parcelable

@Parcelize
data class StockData(
    @SerializedName("01. symbol")
    val symbol: String = "Stock not found",
    @SerializedName("02. open")
    val open: String = "N.A",
    @SerializedName("03. high")
    val high: String = "N.A",
    @SerializedName("04. low")
    val low: String = "N.A",
    @SerializedName("05. price")
    val price: String = "N.A") : Parcelable