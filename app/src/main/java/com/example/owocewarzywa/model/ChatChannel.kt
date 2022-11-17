package com.example.owocewarzywa.model

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}

data class updatedChatChannel(val userIds: MutableList<String>, val toReadBy: String) {
    constructor() : this(mutableListOf(), "")
}
