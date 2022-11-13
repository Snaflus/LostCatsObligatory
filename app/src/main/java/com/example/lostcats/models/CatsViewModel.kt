package com.example.lostcats.models

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lostcats.repositories.LostCatsRepository
import java.text.DateFormat
import java.util.*

class CatsViewModel : ViewModel() {
    private val repository = LostCatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    fun getSort(sort_by: String) {
        var data: List<Cat> = catsLiveData.value!!

        if (sort_by.isNotBlank()) {
            when (sort_by) {
                "name" -> data = catsLiveData.value!!.sortedBy { it.name }
                "place" -> data = catsLiveData.value!!.sortedBy { it.place }
                "reward" -> data = catsLiveData.value!!.sortedByDescending { it.reward }
                "date" -> data = catsLiveData.value!!.sortedByDescending { it.date }
                else -> {
                    Log.d("KIWI", "incorrect sorting parameter")
                }
            }
        }

        if (sort_by.isBlank()) {
            reload()
            return
        }
        repository.getSort(data)
    }

    fun getFilter(filter: String) {
        var data: List<Cat> = catsLiveData.value!!

        if (filter.isNotBlank()) {
            data = data.filter {
                it.name.lowercase().contains(filter.lowercase()) ||
                        it.place.lowercase().contains(filter.lowercase()) ||
                        it.reward >= (filter.toIntOrNull() ?: 0)
            }
        }

        if (filter.isBlank()) {
            reload()
            return
        }
        repository.getFilter(data)
    }

    operator fun get(id: Int): Cat? {
        return catsLiveData.value?.get(id)
    }

    fun add(data: Cat) {
        repository.add(data)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}