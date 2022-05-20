package com.app_actions.android.stocktracker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  companion object {

    const val SYMBOL_QUERY = "symbol"
    const val DEEPLINK_HOME = "/home"
    const val DEEPLINK_QUERY = "/query"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    intent?.handleIntent()

    getQuoteButton.setOnClickListener {
      stockEditText.hideKeyboard()
      if (stockEditText.text.isNotEmpty()) {
        val getQuoteIntent = Intent(this, StockQuoteActivity::class.java).apply {
          putExtra(SYMBOL_QUERY, stockEditText.text.toString())
        }
        startActivity(getQuoteIntent)
      } else {
        Snackbar.make(findViewById(android.R.id.content), R.string.empty_stock,
            Snackbar.LENGTH_SHORT).show()
      }
    }
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    intent?.handleIntent()
  }


  private fun Intent.handleIntent() {
    when (action) {
      Intent.ACTION_VIEW -> handleDeepLink(data)
    }
  }

  private fun handleDeepLink(data: Uri?) {
    when (data?.path) {

      DEEPLINK_HOME -> {
        Snackbar.make(findViewById(android.R.id.content), R.string.welcome_message,
            Snackbar.LENGTH_SHORT).show()
      }

      DEEPLINK_QUERY -> {
        val symbol = data.getQueryParameter(SYMBOL_QUERY).orEmpty()
        val getQuoteIntent = Intent(this, StockQuoteActivity::class.java).apply {
          putExtra(SYMBOL_QUERY, symbol.toUpperCase(Locale.getDefault()))
        }
        startActivity(getQuoteIntent)
      }
    }
  }

  private fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }
}
