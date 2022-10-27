package com.example.lostcats.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.lostcats.repositories.UsersRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*

open class UsersViewModel : ViewModel() {
    private val repository = UsersRepository()
    val userLiveData = repository.userLiveData
    val errorLiveData = repository.errorLiveData

    fun signInWithEmailAndPassword(email: String, password: String) {
        repository.signInWithEmailAndPassword(email, password)
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        repository.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        repository.signOut()
    }
}