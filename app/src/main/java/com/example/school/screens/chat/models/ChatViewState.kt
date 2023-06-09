package com.example.school.screens.chat.models

data class ChatViewState(
    val isLoading: Boolean = true,
    var counter: Int = 0,
    var messages: MutableList<MessageModel> = mutableListOf(),
)

data class MessageModel(
    val name: String = "",
    val text: String = "",
    val time: String = "",
    val sender: String = "",
    var isMine: Boolean = false,
    var isMoreOne: Boolean = false,
    var date: String = "",
    var isNewDate: Boolean = false,
    var isPaddingMine: Boolean = false,
)