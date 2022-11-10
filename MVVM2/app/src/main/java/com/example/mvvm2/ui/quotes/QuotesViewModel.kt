package com.example.mvvm2.ui.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvm2.data.Quote
import com.example.mvvm2.data.QuoteRepository

class QuotesViewModel(private val quoteRepository: QuoteRepository): ViewModel() {

    fun getQuotes() = quoteRepository.getQuotes()

    fun addQuote(quote: Quote) = quoteRepository.addQuote(quote)

}