package com.example.mvvm2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeQuoteDao {
    private val queteList = mutableListOf<Quote>()
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.value = queteList
    }

    fun addQuote(quote: Quote){
        queteList.add(quote)
        quotes.value = queteList
    }

    fun getQuotes() = quotes as LiveData<List<Quote>>
}
