package com.example.school.screens.chat.models

data class ChatViewState(
    val isLoading: Boolean = true,
    var messages: MutableList<MessageModel> = mutableListOf()
)

data class MessageModel(
    val name: String = "",
    val text: String = "",
    val time: String = "",
    var isMine: Boolean = false
)