package com.t.familymanagers.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int>
    get() = _index

    fun setIndex(index: Int) {
        Log.d("PageViewModel","setIndex $index")
        _index.value = index
    }
}