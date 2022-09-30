package com.example.lostcats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatsViewModel : ViewModel() {
    //private var _cats = mutableListOf<Cat>()
    private var mutableLiveData: MutableLiveData<List<Cat>> = MutableLiveData()

    //for debugging without REST API service
    private val _cats = mutableListOf<Cat>(
        Cat(1, "Döner", "Blød", "Køge", 1000, "Alan", System.currentTimeMillis()/1000, ""),
        Cat(2, "Egon", "Hård", "Roskilde", 2000, "Bob", System.currentTimeMillis()/1000, ""),
        Cat(3, "Fiat", "Som pigtråd", "Århus", 5000, "Chris", System.currentTimeMillis()/1000, "")
    )

    val cats: LiveData<List<Cat>> = mutableLiveData

    operator fun get(userId: String, place: String, sort_by: String): MutableList<Cat> {
        var data = _cats

        // TODO: isNotBlank may need to be changed to isNullOrBlank

        if (userId.isNotBlank()) {
            data = data.filter { it.userId == userId } as MutableList<Cat>
        }

        if (place.isNotBlank()) {
            data = data.filter { it.place == place } as MutableList<Cat>
        }

        if (sort_by.isNotBlank()) {
            when (sort_by) {
                "id" -> data.sortBy { it.id }
                "name" -> data.sortBy { it.name }
                "place" -> data.sortBy { it.place }
                "reward" -> data.sortBy { it.reward }
                "userId" -> data.sortBy { it.userId }
                "date" -> data.sortBy { it.date }
                else -> {
                    println("invalid sort_by")
                }
            }
        }
        return data
    }

    fun add(data: Cat) {
        data.id = 0 //ensures id is not null, API assigns it anyway
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