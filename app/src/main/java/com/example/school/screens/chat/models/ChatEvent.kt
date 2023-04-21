package com.example.school.screens.chat.models

sealed class ChatEvent {
    object GetMessages : ChatEvent()
    data class SendMessage(val text: String) : ChatEvent()
}
