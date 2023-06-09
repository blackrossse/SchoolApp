package com.example.school.database

import android.util.Log
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.screens.chat.models.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FirebaseRealtimeDatabaseServiceImpl : FirebaseRealtimeDatabaseService {

    private val database = Firebase.database
    private val messagesRef = database.getReference("messages")

    override fun getMessages(): Flow<MutableList<MessageModel>> {

        return callbackFlow {
            val messages: MutableList<MessageModel> = mutableListOf()

            val messagesListener = object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue<MessageModel>()

                    if (message?.sender.equals(Firebase.auth.currentUser?.uid)) {
                        message?.isMine = true
                    }

                    message?.let {
                        messages.add(0, it)
                    }
                    trySend(messages)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            messagesRef.addChildEventListener(messagesListener)

            awaitClose()
        }
    }

    override fun sendMessage(message: MessageModel) {
        messagesRef.push().setValue(message)
    }
}