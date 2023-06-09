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
import com.example.school.screens.chat.models.MessageModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class ChatViewModel(
    private var repository: Repository = Repository(FirebaseRealtimeDatabaseServiceImpl())
) : ViewModel(), EventHandler<ChatEvent> {

    // Screen state
    private val _uiState = MutableStateFlow(ChatViewState())
    val uiState: StateFlow<ChatViewState> = _uiState.asStateFlow()

    // Vars for check last sender and date
    private var _messagesItemsSenders = mutableListOf<String>()
    private var _messagesDates = mutableListOf<String>()
    private var _messagesPaddingMineAfterLastOther = mutableListOf<Boolean>()

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

        val message = MessageModel(
            name = Firebase.auth.currentUser?.displayName.toString(),
            text = text,
            time = time.format(calendar.time),
            isMine = false,
            sender = Firebase.auth.currentUser?.uid.toString(),
            date = currentDate.dayOfMonth.toString() + " " + currentMonthName,
            isNewDate = false
        )

        viewModelScope.launch {
            repository.addMessage(message)

            _uiState.emit(_uiState.value.copy(counter = ++_uiState.value.counter))
        }
    }

    private fun getMessages() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            var i = 0
            repository.getMessages()
                .map { messages ->
                    _messagesItemsSenders.add(messages.get(0).sender)
                    _messagesDates.add(messages.get(0).date)
                    _messagesPaddingMineAfterLastOther.add(messages.get(0).isMine)

                    // Check for new sender
                    if ((_messagesItemsSenders.size > 1) && (!messages.get(0).isMine) and
                            (_messagesItemsSenders[i] == _messagesItemsSenders[i-1])) {
                        messages.get(0).isMoreOne = true
                    }

                    // Check for new date
                    messages.get(0).isNewDate =
                        ((_messagesDates.size > 1) && _messagesDates[i] != _messagesDates[i-1])

                    // Set padding for first message that called "isMine"
                    messages.get(0).isPaddingMine = (_messagesPaddingMineAfterLastOther.size > 1) &&
                            (messages.get(0).isMine) and
                            (_messagesPaddingMineAfterLastOther[i]
                                    != _messagesPaddingMineAfterLastOther[i-1])

                    i += 1
                    ChatViewState(messages = messages, isLoading = false)
                }
                .collect() { newState ->
                    _uiState.emit(newState)
                }
        }
    }
}