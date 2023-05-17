package com.example.school.database

import com.example.school.screens.chat.models.ChatEvent
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.screens.chat.models.MessageModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface FirebaseRealtimeDatabaseService {
    fun getMessages(): Flow<MutableList<MessageModel>>
    fun sendMessage(message: MessageModel)
}