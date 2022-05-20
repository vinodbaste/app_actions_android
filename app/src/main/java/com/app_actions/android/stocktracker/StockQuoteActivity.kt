
package com.app_actions.android.stocktracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_stock_quote.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class StockQuoteActivity : AppCompatActivity() {

  companion object {

    const val API_FUNCTION = "GLOBAL_QUOTE"

    // Sample Api key - replace with your key
    const val API_KEY = "G7JZRVPHM6QXUIWK887"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_stock_quote)
    val stockSymbol = intent.getStringExtra(MainActivity.SYMBOL_QUERY)
    getStockQuote(stockSymbol!!)
  }

  private fun getStockQuote(stockSymbol: String) {
    val stockApiService = StockApi.stockApiService

    CoroutineScope(Dispatchers.IO).launch {
      try {
        val response = stockApiService.getStockQuote(API_FUNCTION,
            stockSymbol.toUpperCase(Locale.getDefault()), API_KEY)

        withContext(Dispatchers.Main) {
          if (response.isSuccessful) {
            getQuoteProgress.visibility = View.GONE
            stockQuoteCard.visibility = View.VISIBLE
            val stock = response.body()
            updateStockData(stock?.stockData)
          } else {
            getQuoteProgress.visibility = View.GONE
            Snackbar.make(findViewById(android.R.id.content), response.code().toString(),
                Snackbar.LENGTH_SHORT).show()
          }
        }
      } catch (e: Exception) {
        withContext(Dispatchers.Main) {
          getQuoteProgress.visibility = View.GONE
          Snackbar.make(findViewById(android.R.id.content), R.string.error_api,
              Snackbar.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun updateStockData(stockData: StockData?) {
    symbolValue.text = stockData?.symbol
    priceValue.text = stockData?.price
    highValue.text = stockData?.high
    lowValue.text = stockData?.low
  }
}