package com.example.school.database

import android.util.Log
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.screens.chat.models.MessageModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class FirebaseRealtimeDatabaseServiceImpl : FirebaseRealtimeDatabaseService {

    private val database = Firebase.database
    private val messagesRef = database.getReference("messages")

    var messages: MutableList<MessageModel> = mutableListOf()

    override fun getMessages(): Flow<MessageModel?> {
        return callbackFlow {
            val messagesListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue<MessageModel>()

                    trySend(message)
                 }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }

            messagesRef.addChildEventListener(messagesListener)

            awaitClose()

        }.flowOn(Dispatchers.IO)
    }

    override fun sendMessage(message: MessageModel) {
        messagesRef.push().setValue(message)
    }


}