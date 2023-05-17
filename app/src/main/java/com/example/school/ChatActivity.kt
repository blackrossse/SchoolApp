package com.example.school

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        * 1. Разобраться с клавиатурой #
        * 2. Изменить цвет при isMine формы сообщения #
        * 3. Сделать чтобы сообщения когда отправляются
        *       от одного человека шли вместе и уже без ника пользователя #
        * 4. Сделать чтобы показывало день в который отправлена была группа сообщений
        *
        * */

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
