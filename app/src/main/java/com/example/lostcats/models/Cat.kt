package com.example.lostcats.models

import java.text.DateFormat

data class Cat (
    val id: Int,
    val name: String,
    val description: String,
    val place: String,
    val reward: Int,
    val userId: String,
    val date: Long,
    val pictureUrl: String) {
    constructor(name: String, description: String, place: String, reward: Int, userId: String, pictureUrl: String):
            this(-1,name,description,place,reward,userId,System.currentTimeMillis()/1000,pictureUrl)

    override fun toString(): String{
        return "$id $name $description $place $reward $userId $date $pictureUrl"
    }

    fun humanDate(): String {
        return DateFormat.getDateInstance().format(date * 1000L)
    }
}