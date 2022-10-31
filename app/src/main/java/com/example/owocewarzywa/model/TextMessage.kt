package com.example.owocewarzywa.model

import java.util.*

data class TextMessage(val text: String, val time: Date, val senderId: String) {
    constructor() : this(text = "", Date(0), senderId = "")
}
