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

@Composable
fun MessageItem(
    messageItem: Map<String, Any>,
) {
    Column {
        // Show the date of group messages
        if (messageItem["isNewDate"] as Boolean) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    backgroundColor = Color(0xFF3C3F41),
                ) {
                    Text(
                        text = messageItem["date"].toString(),
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 3.dp, start = 6.dp, end = 6.dp),

                        color = Color.White,
                        fontSize = 13.sp,
                    )
                }

            }
        }

        // Area of message
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement =
            if (messageItem["isMine"] as Boolean) Arrangement.End else Arrangement.Start
        ) {

            // Time card of message (if mine)
            if (messageItem["isMine"] as Boolean) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .align(Alignment.Bottom),
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor = Color(0xFF3C3F41),
                    contentColor = Color.White
                ) {
                    Text(
                        modifier = Modifier
                            .alpha(.9f)
                            .padding(top = 2.dp, bottom = 2.dp, start = 5.dp, end = 5.dp),
                        text = messageItem["time"].toString(),
                        fontSize = 10.sp
                    )
                }
            }

            // Message Card
            Card(
                modifier = Modifier
                    .widthIn(max = 350.dp)
                    .padding(
                        start = if (messageItem["isMine"] as Boolean) 5.dp else 10.dp,
                        end = if (messageItem["isMine"] as Boolean) 10.dp else 5.dp,
                        bottom = 4.dp,
                        top =
                        if (messageItem["isMine"] as Boolean xor messageItem["isPaddingMine"] as Boolean) 1.dp
                        else if (messageItem["isPaddingMine"] as Boolean) 15.dp
                        else if (messageItem["isMoreOne"] as Boolean) .5.dp
                        else 15.dp
                    ),
                shape = RoundedCornerShape(15.dp),
                backgroundColor =
                if (messageItem["isMine"] as Boolean) Color(0xFF4A109C) else Color(0xFF292829),
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

                    if (!(messageItem["isMine"] as Boolean) xor messageItem["isMoreOne"] as Boolean) {
                        Text(
                            modifier = Modifier,
                            text = messageItem["name"].toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    Text(
                        text = messageItem["text"].toString(),
                        fontSize = 16.sp
                    )

                }

            }

            // Time card of message (if not mine)
            if (!(messageItem["isMine"] as Boolean)) {
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
                        text = messageItem["time"].toString(),
                        fontSize = 9.sp
                    )
                }
            }


        }
    }
}

@Preview (showBackground = true)
@Composable
fun MessageItemPreview() {

}