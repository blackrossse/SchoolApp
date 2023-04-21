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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Repository(
    private val firebaseRealtimeDatabaseService: FirebaseRealtimeDatabaseServiceImpl
) {

    fun getMessages(): Flow<MessageModel?> {
        return firebaseRealtimeDatabaseService.getMessages()
    }

    suspend fun addMessage(message: MessageModel) {
        firebaseRealtimeDatabaseService.sendMessage(message)
    }

/*    suspend fun readData(): MutableList<MessageModel> {
        // Ошибка именно где-то тут. Надо разобраться.
        val messagesListener = object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

                val messages = mutableListOf<MessageModel>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(MessageModel::class.java)
                    item?.let { messages.add(it) }
                }шеуь


                val message = snapshot.getValue<MessageModel>()
                Log.d("myMessage", message.toString())
                message?.let { messages.add(0, it) }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        messagesRef.addChildEventListener(messagesListener)
    }

    suspend fun addMessage(message: MessageModel) {
        messagesRef.push().setValue(message)
    }*/
}