
package com.app_actions.android.stocktracker

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.alphavantage.co/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface StockApiService {

  @GET("/query")
  suspend fun getStockQuote(@Query("function") function: String,
      @Query("symbol") symbol: String, @Query("apikey") apiKey: String):
      Response<Stock>
}

object StockApi {
  val stockApiService: StockApiService by lazy {
    retrofit.create(StockApiService::class.java)
  }
}