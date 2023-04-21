package com.example.school

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.school.screens.ChatScreen
import com.example.school.screens.chat.ChatViewModel
import com.example.school.screens.chat.models.ChatEvent
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.ui.theme.SchoolTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : ComponentActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Not to hide bottom margin when opening keyboard
        val window = this.window
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            val chatViewModel: ChatViewModel = viewModel()
            val chatUiState by chatViewModel.uiState.collectAsState(ChatViewState())

            SchoolTheme() {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    // Composable
                    ChatScreen(
                        messages = chatUiState.messages,
                        isLoading = chatUiState.isLoading
                    ) {
                        chatViewModel.obtainEvent(ChatEvent.SendMessage(it))
                    }
                }
            }

            LaunchedEffect(key1 = Unit, block = {
                chatViewModel.obtainEvent(ChatEvent.GetMessages)
            })
        }

    }
}
