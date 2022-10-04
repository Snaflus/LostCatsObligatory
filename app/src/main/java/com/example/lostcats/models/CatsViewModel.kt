package com.example.lostcats.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lostcats.repository.LostCatsRepository
import java.text.DateFormat
import java.util.*

class CatsViewModel : ViewModel() {
    private val repository = LostCatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    init {
        reload()
    }

    fun reload(){
        repository.getPosts()
    }

    operator fun get(userId: String, place: String, sort_by: String): List<Cat> {
        var data = catsLiveData as MutableList<Cat> //convert to mutablelist for filtering

        // TODO: isNotBlank may need to be changed to !isNullOrBlank

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

                "idDesc" -> data.sortByDescending { it.id }
                "nameDesc" -> data.sortByDescending { it.name }
                "placeDesc" -> data.sortByDescending { it.place }
                "rewardDesc" -> data.sortByDescending { it.reward }
                "userIdDesc" -> data.sortByDescending { it.userId }
                "dateDesc" -> data.sortByDescending { it.date }

                else -> {
                    println("invalid sort_by")
                }
            }
        }
        return data
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

    fun humanDate(date: Long): String {
        return DateFormat.getDateInstance().format(date * 1000L)
    }
}