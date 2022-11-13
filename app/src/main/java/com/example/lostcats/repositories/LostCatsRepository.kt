package com.example.lostcats.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lostcats.models.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LostCatsRepository {
    private val url = "https://anbo-restlostcats.azurewebsites.net/api/"

    private val lostCatsService: LostCatsService
    val catsLiveData: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        lostCatsService = build.create(LostCatsService::class.java)
        //getPosts() //potentially redundant, is called in CatsViewModel init
    }

    fun getPosts() {
        lostCatsService.getAllCats().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    val b: List<Cat>? = response.body()
                    catsLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("KIWI", message)
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("KIWI", t.message!!)
            }
        })
    }

    fun getSort(list: List<Cat>) {
        if (list.isNotEmpty()) {
            catsLiveData.postValue(list)
        } else {
            val message = "Repository error in getSort"
            errorMessageLiveData.postValue(message)
            Log.d("KIWI", message)
        }
    }

    fun add(cat: Cat) {
        lostCatsService.saveCat(cat).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("KIWI", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("KIWI", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("KIWI", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        lostCatsService.deleteCat(id).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("KIWI", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("KIWI", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("KIWI", t.message!!)
            }
        })
    }
}