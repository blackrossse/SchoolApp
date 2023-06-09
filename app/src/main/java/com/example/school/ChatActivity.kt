package com.example.school

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.school.screens.ChatScreen
import com.example.school.screens.chat.ChatViewModel
import com.example.school.screens.chat.models.ChatEvent
import com.example.school.screens.chat.models.ChatViewState
import com.example.school.ui.theme.SchoolTheme
import java.util.*

class ChatActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
    @SuppressLint("SimpleDateFormat", "LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val chatViewModel: ChatViewModel = viewModel()

            SchoolTheme() {
                Surface (
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    ChatScreen(
                        chatViewModel = chatViewModel
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
