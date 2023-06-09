package com.example.school.screens.chat

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school.base.EventHandler
import com.example.school.database.FirebaseRealtimeDatabaseServiceImpl
import com.example.school.database.Repository
import com.example.school.screens.chat.models.ChatEvent
import com.example.school.screens.chat.models.ChatViewState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class ChatViewModel() : ViewModel(), EventHandler<ChatEvent> {

    private val db = Firebase.firestore

    // Screen state
    private val _uiState = MutableStateFlow(ChatViewState())
    val uiState: StateFlow<ChatViewState> = _uiState.asStateFlow()

    // Vars for check last sender and date
    private var _messagesItemsSenders = mutableListOf<String>()
    private var _messagesDates = mutableListOf<String>()
    private var _messagesPaddingMineAfterLastOther = mutableListOf<Boolean>()

    private var i = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun obtainEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.GetMessages -> getMessages()
            is ChatEvent.SendMessage -> sendMessage(text = event.text)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun sendMessage(text: String) {
        val calendar: Calendar = Calendar.getInstance()
        val currentDate = LocalDate.now()

        val time = SimpleDateFormat("HH:mm")
        val currentMonthName = currentDate.month.getDisplayName(
            TextStyle.FULL, Locale.getDefault()
        )

        db.collection("messages").document().set(
            hashMapOf(
                "name" to Firebase.auth.currentUser?.displayName.toString(),
                "text" to text,
                "time" to time.format(calendar.time),
                "isMine" to false,
                "sender" to Firebase.auth.currentUser?.uid.toString(),
                "date" to currentDate.dayOfMonth.toString() + " " + currentMonthName,
                "isNewDate" to false,
                "isMoreOne" to false,
                "isPaddingMine" to false,
                "millis" to System.currentTimeMillis()
            )
        )
    }

    private fun getMessages() {
        db.collection("messages")
            .orderBy("millis")
            .addSnapshotListener { value, e ->
                val messages = emptyList<Map<String, Any>>().toMutableList()

                if (value != null) {
                    for (doc in value) {
                        val data = doc.data

                        _messagesItemsSenders.add(data["sender"].toString())
                        _messagesDates.add(data["date"].toString())
                        _messagesPaddingMineAfterLastOther.add(data["isMine"] as Boolean)

                        data["isMine"] =
                            Firebase.auth.currentUser?.uid.toString() == data["sender"]

                        // Check for new sender
                        if ((_messagesItemsSenders.size > 1) && (!(data["isMine"] as Boolean)) and
                            (_messagesItemsSenders[i] == _messagesItemsSenders[i-1])) {
                            data["isMoreOne"] = true
                        }

                        // Check for new date
                        data["isNewDate"] =
                            ((_messagesDates.size > 1) && _messagesDates[i] != _messagesDates[i-1])

                        // Set padding for first message that called "isMine"
                        data["isPaddingMine"] =
                            (_messagesPaddingMineAfterLastOther.size > 1) &&
                                    (data["isMine"] == true) and
                                    (_messagesPaddingMineAfterLastOther[i] !=
                                            _messagesPaddingMineAfterLastOther[i-1])

                        i += 1
                        messages.add(data)
                    }
                }
                updateMessages(messages)
            }
    }

    private fun updateMessages(messages: MutableList<Map<String, Any>>) {
        _uiState.value = ChatViewState(messages = messages.asReversed(), isLoading = false)
    }
}