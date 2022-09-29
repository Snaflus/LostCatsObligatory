package com.example.lostcats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatsViewModel: ViewModel() {
    private var _cats = mutableListOf<Cat>()
    private var mutableLiveData:MutableLiveData<List<Cat>> = MutableLiveData()

    val cats: LiveData<List<Cat>> = mutableLiveData

//    operator fun get(userId: String, place: String, sort_by: String): Cat {
//        return _cats
//    }

    fun add(data: Cat) {
        _cats.add(data)
        mutableLiveData.value = _cats
    }

    operator fun get(position: Int): Cat {
        return _cats[position]
    }

    fun delete(data: Cat) {
        _cats.add(data)
        mutableLiveData.value = _cats
    }

}