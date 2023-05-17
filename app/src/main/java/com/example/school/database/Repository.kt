package com.example.school.database

import android.os.Message
import android.util.Log
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.screens.chat.models.MessageModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Repository(
    private val firebaseRealtimeDatabaseService: FirebaseRealtimeDatabaseServiceImpl
) {

    suspend fun getMessages(): Flow<MutableList<MessageModel>> {
        return firebaseRealtimeDatabaseService.getMessages()
    }

    suspend fun addMessage(message: MessageModel) {
        firebaseRealtimeDatabaseService.sendMessage(message)
    }
}