package com.example.lostcats.repositories

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.lostcats.LoginFragmentDirections
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class UsersRepository() {
    private val auth = Firebase.auth
    val userLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData<FirebaseUser?>()

    init {
        userLiveData.value = auth.currentUser
    }

    fun signInWithEmailAndPassword(email: String, password: String, navController: NavController) : CharSequence {
        var exceptionString: CharSequence = ""

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = auth.currentUser
                val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                navController.navigate(action)
            } else {
                exceptionString = task.exception?.message.toString()
            }
        }
        return exceptionString
    }

    fun createUserWithEmailAndPassword(email: String, password: String, navController: NavController) : CharSequence {
        var exceptionString: CharSequence = ""

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = auth.currentUser
                signInWithEmailAndPassword(email, password, navController)
            } else {
                exceptionString = task.exception?.message.toString()
            }
        }
        return exceptionString
    }

    fun signOut() {
        auth.signOut()
        userLiveData.value = auth.currentUser
    }

    fun currentUser(): FirebaseUser? {
        userLiveData.value = auth.currentUser
        return auth.currentUser
    }
}