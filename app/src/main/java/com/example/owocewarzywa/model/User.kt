package com.example.owocewarzywa.model

data class User(val name: String, val bio: String, val profilePicturePath: String?) {
    constructor() :
            this(name = "", bio = "", profilePicturePath = null)
}