package com.example.school.javacode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.school.ChatActivity;
import com.example.school.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;
    private TextView timetableTextView;

    private Date dt;
    private int hours;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        timeTextView = findViewById(R.id.timeTextView);
        timetableTextView = findViewById(R.id.timetableTextView);

        // Underline for text of timetable
        timetableTextView.setPaintFlags(
                timetableTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Start activity of timetable
        timetableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TimetableActivity.class);
                startActivity(i);
            }
        });

        // Start activity of chat
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        CountDownTimer newtimer = new CountDownTimer(1000000000, 60000) {
            public void onTick(long millisUntilFinished) {
                dt = new Date();
                hours = dt.getHours();
                minutes = dt.getMinutes();

                if (hours < 10 && minutes < 10) {
                    timeTextView.setText("0" + hours + ":" + "0" + minutes);
                } else if (hours >= 10 && minutes >= 10) {
                    timeTextView.setText(hours + ":" + minutes);
                } else if (hours >= 10 && minutes < 10) {
                    timeTextView.setText(hours + ":" + "0" + minutes);
                } else if (hours < 10 && minutes >= 10) {
                    timeTextView.setText("0" + hours + ":" + minutes);
                }
            }

            public void onFinish() {

            }
        };
        newtimer.start();

        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    switch (view.getId()) {
                        case R.id.timeTextView:
                            timeTextView.setTextColor(getResources()
                                    .getColorStateList(R.color.grey));
                            break;
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    switch (view.getId()) {
                        case R.id.timeTextView:
                            timeTextView.setTextColor(getResources()
                                    .getColorStateList(R.color.white));
                            Intent i = new Intent(MainActivity.this,
                                    TimeLessonsActivity.class);
                            startActivity(i);
                            break;
                    }
                }

                return true;
            }
        };

        timeTextView.setOnTouchListener(listener);
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.AlertDialogCustom);
        builder.setMessage("Выйти?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                exit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}