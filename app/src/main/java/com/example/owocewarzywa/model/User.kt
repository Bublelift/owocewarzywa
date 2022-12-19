package com.example.owocewarzywa.model

data class User(val name: String, val bio: String, val profilePicturePath: String?, val score: Long?) {
    constructor() :
            this(name = "", bio = "", profilePicturePath = null, score = 0L)
}