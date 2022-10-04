package com.example.lostcats.models

data class Cat (
    var id: Int,
    var name: String,
    var description: String,
    var place: String,
    var reward: Int,
    var userId: String,
    var date: Long,
    var pictureUrl: String
)