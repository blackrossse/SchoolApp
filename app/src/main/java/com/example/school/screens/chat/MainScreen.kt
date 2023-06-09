package com.example.school.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.WindowInsets
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.ViewCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.school.screens.chat.ChatViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import com.example.school.screens.chat.models.ChatViewState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    onClickSendMessage: (String) -> Unit,
) {
    val viewState by chatViewModel.uiState.collectAsState()

    var text by remember {
        mutableStateOf("")
    }

    // Chat area
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (list, textfield) = createRefs()

        if (viewState.isLoading) {
            Box(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(textfield.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 5.dp,
                    modifier = Modifier.size(70.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(textfield.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                reverseLayout = true
            ) {
                viewState.messages.forEachIndexed { _, item ->
                    item {
                        MessageItem(item)
                    }
                }
            }
        }

        // Field input
        Box(
            modifier = Modifier
                .constrainAs(textfield) {
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                    end.linkTo(parent.end, margin = 7.dp)
                    width = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.CenterStart
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(fraction = .85f),
                value = text,
                onValueChange = { text = it },
                label = { Text("Сообщение") },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                maxLines = 5,
                textStyle = TextStyle(fontSize = 17.sp)
            )

            Button(
                modifier = Modifier
                    .size(55.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                ,
                onClick = {
                    if (!text.trim().isEmpty()) {
                        onClickSendMessage(text.trim())
                        text = ""
                    }
                          },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "sendMessageIcon",
                    modifier = Modifier
                        .size(120.dp)
                )
            }
        }

    }
}

@Preview (showBackground = true)
@Composable
fun ChatScreenPreview() {
/*    ChatScreen(
        messages =
        ,
        onClickSendMessage =
    )*/
}
