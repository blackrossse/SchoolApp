package com.example.school.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school.screens.chat.models.MessageModel

@Composable
fun MessageItem(messageItem: MessageModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(fraction = .8f)
                .padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        top = 8.dp,
                        bottom = 5.dp,
                        end = 12.dp
                    )
            ) {
                Text(
                    text = messageItem.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = messageItem.text,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(.9f),
                    text = messageItem.time,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun MessageItemPreview() {
    MessageItem(
        messageItem = MessageModel(
        "Nick",
        "всем привет!",
        "12:43"
    )
    )
}