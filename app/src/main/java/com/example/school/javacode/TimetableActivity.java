package com.example.school.javacode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.school.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class TimetableActivity extends AppCompatActivity {

    private ListView mondayListView;
    private ListView tuesdayListView;
    private ListView wednesdayListView;
    private ListView thursdayListView;
    private ListView fridayListView;
    private ListView saturdayListView;

    private TextView mondayTextView;
    private TextView tuesdayTextView;
    private TextView wednesdayTextView;
    private TextView thursdayTextView;
    private TextView fridayTextView;
    private TextView saturdayTextView;

    private String[] monday = {"1. Разговоры о важном", "2. Химия", "3. История", "4. Русский язык",
            "5. Право", "6. Физ-ра", "7. Математика"};
    private String[] tuesday = {"1. Право", "2. Математика", "3. Граждановедение", "4. Биология",
            "5. Физика", "6. Английский язык"};
    private String[] wednesday = {"1. Русский язык", "2. Английский язык", "3. Общество", "4. Физ-ра",
            "5. Экономика", "6. Математика", "7. Экономика"};
    private String[] thursday = {"1. Физика", "2. Кубановедение", "3. Алгебра", "4. Лит-ра",
            "5. Индивидуальный проект", "6. Лит-ра"};
    private  String[] friday = {"1. География", "2. История", "3. Математика", "4. Физ-ра",
            "5. Английский язык", "6. Лит-ра"};
    private String[] saturday = {"1. Русский язык", "2. Общество", "3. Математика", "4. ОБЖ",
            "5. Русский язык", "6. Математика"};

    Date dt = new Date();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        mondayListView = findViewById(R.id.mondayListView);
        tuesdayListView = findViewById(R.id.tuesdayListView);
        wednesdayListView = findViewById(R.id.wednesdayListView);
        thursdayListView = findViewById(R.id.thursdayListView);
        fridayListView = findViewById(R.id.fridayListView);
        saturdayListView = findViewById(R.id.saturdayListView);

        mondayTextView = findViewById(R.id.mondayTextView);
        tuesdayTextView = findViewById(R.id.tuesdayTextView);
        wednesdayTextView = findViewById(R.id.wednesdayTextView);
        thursdayTextView = findViewById(R.id.thursdayTextView);
        fridayTextView = findViewById(R.id.fridayTextView);
        saturdayTextView = findViewById(R.id.saturdayTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, monday);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tuesday);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, wednesday);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, thursday);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, friday);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, saturday);

        mondayListView.setAdapter(adapter);
        tuesdayListView.setAdapter(adapter2);
        wednesdayListView.setAdapter(adapter3);
        thursdayListView.setAdapter(adapter4);
        fridayListView.setAdapter(adapter5);
        saturdayListView.setAdapter(adapter6);

        String day = getDayStringNew(LocalDate.now(), new Locale("en"));
        switch (day) {
            case "Tuesday":
                mondayTextView.setVisibility(View.GONE);
                mondayListView.setVisibility(View.GONE);
                break;
            case "Wednesday":
                mondayTextView.setVisibility(View.GONE);
                mondayListView.setVisibility(View.GONE);
                tuesdayTextView.setVisibility(View.GONE);
                tuesdayListView.setVisibility(View.GONE);
                break;
            case "Thursday":
                wednesdayTextView.setVisibility(View.GONE);
                wednesdayListView.setVisibility(View.GONE);
                mondayTextView.setVisibility(View.GONE);
                mondayListView.setVisibility(View.GONE);
                tuesdayTextView.setVisibility(View.GONE);
                tuesdayListView.setVisibility(View.GONE);
                break;
            case "Friday":
                thursdayTextView.setVisibility(View.GONE);
                thursdayListView.setVisibility(View.GONE);
                wednesdayTextView.setVisibility(View.GONE);
                wednesdayListView.setVisibility(View.GONE);
                mondayTextView.setVisibility(View.GONE);
                mondayListView.setVisibility(View.GONE);
                tuesdayTextView.setVisibility(View.GONE);
                tuesdayListView.setVisibility(View.GONE);
                break;
            case "Saturday":
                if (dt.getHours() >= 13) {
                    break;
                } else {
                    fridayTextView.setVisibility(View.GONE);
                    fridayListView.setVisibility(View.GONE);
                    thursdayTextView.setVisibility(View.GONE);
                    thursdayListView.setVisibility(View.GONE);
                    wednesdayTextView.setVisibility(View.GONE);
                    wednesdayListView.setVisibility(View.GONE);
                    mondayTextView.setVisibility(View.GONE);
                    mondayListView.setVisibility(View.GONE);
                    tuesdayTextView.setVisibility(View.GONE);
                    tuesdayListView.setVisibility(View.GONE);
                    break;
                }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getDayStringNew(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }
}