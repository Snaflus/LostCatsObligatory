package com.example.lostcats.models

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lostcats.repositories.LostCatsRepository
import java.text.DateFormat

class CatsViewModel : ViewModel() {
    private val repository = LostCatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(userId: String, place: String, sort_by: String){
        var data: List<Cat> = catsLiveData.value!!

//        if (userId.isNotBlank()) {
//            data = data.filter { it.first().userId == userId } as MutableList<List<Cat>>
//        }
//
//        if (place.isNotBlank()) {
//            data = data.filter { it.first().place == place } as MutableList<List<Cat>>
//        }

        if (sort_by.isNotBlank()) {
            when (sort_by) {
                "name" -> data = catsLiveData.value!!.sortedBy { it.name }
                "place" -> data = catsLiveData.value!!.sortedBy { it.place }
                "reward" -> data = catsLiveData.value!!.sortedByDescending { it.reward }
                "date" -> data = catsLiveData.value!!.sortedByDescending { it.date }
                else -> {
                    Log.d("KIWI","incorrect sorting parameter")
                }
            }
        } else {
            reload()
            data = catsLiveData.value!!
        }
        repository.getSort(data)
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