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

    fun signInWithEmailAndPassword(email: String, password: String, navController: NavController) : CharSequence {
        return repository.signInWithEmailAndPassword(email, password, navController)
    }

    fun createUserWithEmailAndPassword(email: String, password: String, navController: NavController) : CharSequence {
        return repository.createUserWithEmailAndPassword(email, password, navController)
    }

    fun signOut() {
        repository.signOut()
    }

    fun currentUser(): FirebaseUser? {
        return repository.currentUser()
    }
}