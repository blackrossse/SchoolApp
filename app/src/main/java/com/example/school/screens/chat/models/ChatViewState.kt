package com.example.school.screens.chat.models

data class ChatViewState(
    val isLoading: Boolean = true,
    // val messages: MutableList<MessageModel> = mutableListOf()
    var messages: List<MessageModel> = emptyList()

)

data class MessageModel(
    val name: String = "",
    val text: String = "",
    val time: String = ""
)