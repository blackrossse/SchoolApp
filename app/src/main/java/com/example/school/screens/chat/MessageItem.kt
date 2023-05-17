package com.example.school.screens

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school.screens.chat.models.MessageModel

@Composable
fun MessageItem(
    messageItem: MessageModel,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement =
            if (messageItem.isMine) Arrangement.End else Arrangement.Start
    ) {

        if (messageItem.isMine) {
            Card(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(6.dp),
                backgroundColor =  Color(0xFF3C3F41),
                contentColor = Color.White
            ) {
                Text(
                    modifier = Modifier
                        .alpha(.9f)
                        .padding(top = 2.dp, bottom = 2.dp, start = 5.dp, end = 5.dp),
                    text = messageItem.time,
                    fontSize = 10.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .widthIn(max = 350.dp)
                .padding(
                    start = if (messageItem.isMine) 5.dp else 10.dp,
                    end = if (messageItem.isMine) 10.dp else 5.dp,
                    bottom = 4.dp,
                    top = if (messageItem.isMine) 1.dp else if (messageItem.isMoreOne) .5.dp else 15.dp
                ),
            shape = RoundedCornerShape(15.dp),
            backgroundColor =
                if (messageItem.isMine) Color(0xFF4A109C) else Color(0xFF292829),
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 6.dp,
                        bottom = 6.dp,
                        end = 12.dp
                    )
            ) {

                if (!messageItem.isMine xor messageItem.isMoreOne) {
                    Text(
                        modifier = Modifier,
                        text = messageItem.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }

                Text(
                    text = messageItem.text,
                    fontSize = 16.sp
                )

            }

        }

        if (!messageItem.isMine) {
            Card(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(6.dp),
                backgroundColor =  Color(0xFF3C3F41),
                contentColor = Color.White
            ) {
                Text(
                    modifier = Modifier
                        .alpha(.9f)
                        .padding(top = 2.dp, bottom = 2.dp, start = 5.dp, end = 5.dp),
                    text = messageItem.time,
                    fontSize = 9.sp
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
        "если честно, не знаю, кому как короч, мне понрав, а тебе? как",
        "12:43",
    ),
    )
}