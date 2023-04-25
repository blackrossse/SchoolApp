package com.example.school.screens.chat

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.school.base.EventHandler
import com.example.school.database.FirebaseRealtimeDatabaseServiceImpl
import com.example.school.database.Repository
import com.example.school.screens.chat.models.ChatEvent
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.screens.chat.models.MessageModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(
    private var repository: Repository = Repository(FirebaseRealtimeDatabaseServiceImpl())
) : ViewModel(), EventHandler<ChatEvent> {

    private val _uiState = MutableStateFlow(ChatViewState())
    val uiState: StateFlow<ChatViewState> = _uiState.asStateFlow()

    override fun obtainEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.GetMessages -> getMessages()
            is ChatEvent.SendMessage -> sendMessage(text = event.text)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendMessage(text: String) {
        val calendar: Calendar = Calendar.getInstance()
        val df: SimpleDateFormat = SimpleDateFormat("HH:mm")

        val message = MessageModel(
            Firebase.auth.currentUser?.displayName.toString(),
            text,
            df.format(calendar.time),
            isMine = true
        )

        viewModelScope.launch {
            repository.addMessage(message)

            val listOfMessages = mutableListOf<MessageModel>()
            repository.getMessages()
                .map() {
                    for (i in it) {
                        listOfMessages.add(i)
                    }
                }

            _uiState.value.messages = listOfMessages
        }
    }

    private fun getMessages() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {

            repository.getMessages()
                .map { messages ->
                    ChatViewState(messages = messages, isLoading = false)
                }
                .collect() { newState ->
                    _uiState.value = newState
                }

        }

    }
}