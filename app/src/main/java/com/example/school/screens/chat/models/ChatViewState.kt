package com.example.school.screens.chat.models

data class ChatViewState(
    val isLoading: Boolean = true,
    var messages: MutableList<Map<String, Any>> = mutableListOf(),
)
