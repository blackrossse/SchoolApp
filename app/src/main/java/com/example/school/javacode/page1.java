package com.example.school.javacode;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page1.
     */
    // TODO: Rename and change types and number of parameters
    public static page1 newInstance(String param1, String param2) {
        page1 fragment = new page1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView topTextView;
    private TextView titleTextView;
    private TextView timeTextView;
    private View rectBlackView;
    private View rectBackgroundView;

    private CountDownTimer countDownTimer;

    private String strLesson;
    private String strBreak;

    private String day;

    private Date dt;
    private int hours;
    private int minutes;
    private int seconds;

    private ConstraintLayout.LayoutParams params;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        topTextView = (TextView) view.findViewById(R.id.topTextView);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeLessonTextView);
        rectBlackView = (View) view.findViewById(R.id.rectBlackView);
        rectBackgroundView = (View) view.findViewById(R.id.rect_1);

        params = (ConstraintLayout.LayoutParams) rectBlackView.getLayoutParams();

        // Надо понять, как сделать так, чтобы прогресс бар нормально работал во время перемены.
        // Ещё, нужно чтобы таймер возобнавлялся сразу после истечения, не перезагружая активити

        setTimeAndProgressBar();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTimeAndProgressBar() {

        dt = new Date();
        hours = dt.getHours();
        minutes = dt.getMinutes();
        seconds = dt.getSeconds();

        topTextView.setPaintFlags(
                topTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        day = getDayStringNew(LocalDate.now(), new Locale("en"));

        strLesson = "Осталось до конца урока:";
        strBreak = "Осталось до конца перемены:";

        if (!day.equals("Saturday") && !day.equals("Monday") && !day.equals("Wednesday")) {
            if (hours >= 8 && hours < 13) {
                setVisible();
            } else {
                setGone();
            }
        } else if (day.equals("Monday") || day.equals("Wednesday")) {
            if (hours >= 8 && hours < 13 || hours == 13 && minutes < 45) {
                setVisible();
            } else {
                setGone();
            }
        } else {
            if (hours >= 8 && hours < 12 || hours == 12 && minutes < 20) {
                setVisible();
            } else {
                setGone();
            }
        }

        if (!day.equals("Saturday") && !day.equals("Sunday")) {
            if (hours == 8 && minutes < 40) {
                // 1ый урок
                titleTextView.setText(strLesson);
                int sec = (40 - minutes) * 60 - seconds;
                startTimer(sec, params, 40, 40);
            } else if (hours == 8 && minutes < 50) {
                // Перемена для 1го урока
                titleTextView.setText(strBreak);
                int sec = (50 - minutes) * 60 - seconds;
                startTimer(sec, params, 50, 10);
            } else if (hours == 8 && minutes <= 59) {
                // 2ой урок
                titleTextView.setText(strLesson);
                int sec = (90 - minutes) * 60 - seconds;
                startTimer(sec, params, 90, 40);
            } else if (hours == 9 && minutes < 30) {
                int sec = (30 - minutes) * 60 - seconds;
                startTimer(sec, params, 30, 40);
            } else if (hours == 9 && minutes < 45) {
                // Перемена для 2го урока
                titleTextView.setText(strBreak);
                int sec = (45 - minutes) * 60 - seconds;
                startTimer(sec, params, 45, 15);
            } else if (hours == 9 && minutes <= 59) {
                // 3ий урок
                titleTextView.setText(strLesson);
                int sec = (85 - minutes) * 60 - seconds;
                startTimer(sec, params, 85, 40);
            } else if (hours == 10 && minutes < 25) {
                int sec = (25 - minutes) * 60 - seconds;
                startTimer(sec, params, 25, 40);
            } else if (hours == 10 && minutes < 40) {
                // Перемена для 3го урока
                titleTextView.setText(strBreak);
                int sec = (40 - minutes) * 60 - seconds;
                startTimer(sec, params, 40, 15);
            } else if (hours == 10 && minutes <= 59) {
                // 4ый урок
                titleTextView.setText(strLesson);
                int sec = (80 - minutes) * 60 - seconds;
                startTimer(sec, params, 80, 40);
            } else if (hours == 11 && minutes < 20) {
                int sec = (20 - minutes) * 60 - seconds;
                startTimer(sec, params, 20, 40);
            } else if (hours == 11 && minutes < 30) {
                // Перемена для 4го урока
                titleTextView.setText(strBreak);
                int sec = (30 - minutes) * 60 - seconds;
                startTimer(sec, params, 30, 10);
            } else if (hours == 11 && minutes <= 59) {
                // 5ый урок
                titleTextView.setText(strLesson);
                int sec = (70 - minutes) * 60 - seconds;
                startTimer(sec, params, 70, 40);
            } else if (hours == 12 && minutes < 10) {
                int sec = (10 - minutes) * 60 - seconds;
                startTimer(sec, params, 10, 40);
            } else if (hours == 12 && minutes < 20) {
                // Перемена для 5го урока
                titleTextView.setText(strBreak);
                int sec = (20 - minutes) * 60 - seconds;
                startTimer(sec, params, 20, 10);
            } else if (hours == 12 && minutes <= 59) {
                // 6ой урок
                titleTextView.setText(strLesson);
                int sec = (60 - minutes) * 60 - seconds;
                startTimer(sec, params, 60, 40);
            } else if (day.equals("Monday") || day.equals("Wednesday")) {
                // Если понедельник или среда, то включаем 7 урок
                if (hours == 13 && minutes < 10) {
                    // Перемена для 6го урока
                    titleTextView.setText(strBreak);
                    int sec = (10 - minutes) * 60 - seconds;
                    startTimer(sec, params, 10, 10);
                } else if (hours == 13 && minutes < 45) {
                    // 7ой урок
                    titleTextView.setText(strLesson);
                    int sec = (45 - minutes) * 60 - seconds;
                    startTimer(sec, params, 45, 35);
                }
            }
        } else if (day.equals("Saturday")) {
            // Суббота
            if (hours == 8 && minutes < 35) {
                // 1ый урок
                titleTextView.setText(strLesson);
                int sec = (35 - minutes) * 60 - seconds;
                startTimer(sec, params, 35, 35);
            } else if (hours == 8 && minutes < 45) {
                // Перемена для 1го урока
                titleTextView.setText(strBreak);
                int sec = (45 - minutes) * 60 - seconds;
                startTimer(sec, params, 45, 10);
            } else if (hours == 8 && minutes <= 59) {
                // 2ой урок
                titleTextView.setText(strLesson);
                int sec = (80 - minutes) * 60 - seconds;
                startTimer(sec, params, 80, 35);
            } else if (hours == 9 && minutes < 20) {
                int sec = (20 - minutes) * 60 - seconds;
                startTimer(sec, params, 20, 35);
            } else if (hours == 9 && minutes < 30) {
                // Перемена для 2го урока
                titleTextView.setText(strBreak);
                int sec = (30 - minutes) * 60 - seconds;
                startTimer(sec, params, 30, 10);
            } else if (hours == 9 && minutes <= 59) {
                // 3ий урок
                titleTextView.setText(strLesson);
                int sec = (65 - minutes) * 60 - seconds;
                startTimer(sec, params, 65, 35);
            } else if (hours == 10 && minutes < 5) {
                int sec = (5 - minutes) * 60 - seconds;
                startTimer(sec, params, 5, 35);
            } else if (hours == 10 && minutes < 15) {
                // Перемена для 3го урока
                titleTextView.setText(strBreak);
                int sec = (15 - minutes) * 60 - seconds;
                startTimer(sec, params, 15, 10);
            } else if (hours == 10 && minutes < 50) {
                // 4ый урок
                titleTextView.setText(strLesson);
                int sec = (50 - minutes) * 60 - seconds;
                startTimer(sec, params, 50, 35);
            } else if (hours == 10 && minutes <= 59) {
                titleTextView.setText(strBreak);
                int sec = (60 - minutes) * 60 - seconds;
                startTimer(sec, params, 60, 10);
            } else if (hours == 11 && minutes < 35) {
                // 5 урок
                titleTextView.setText(strLesson);
                int sec = (35 - minutes) * 60 - seconds;
                startTimer(sec, params, 35, 35);
            } else if (hours == 11 && minutes < 45) {
                titleTextView.setText(strLesson);
                int sec = (45 - minutes) * 60 - seconds;
                startTimer(sec, params, 45, 10);
            } else if (hours == 11 && minutes <= 59) {
                int sec = (80 - minutes) * 60 - seconds;
                startTimer(sec, params, 80, 35);
            } else if (hours == 12 && minutes < 20) {
                titleTextView.setText(strBreak);
                int sec = (20 - minutes) * 60 - seconds;
                startTimer(sec, params, 20, 35);
            }
        } else {
            // Воскресенье
            setGone();
        }
    }

    private void setVisible() {
        topTextView.setVisibility(View.GONE);

        titleTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        rectBlackView.setVisibility(View.VISIBLE);
        rectBackgroundView.setVisibility(View.VISIBLE);
    }

    private void setGone() {
        topTextView.setVisibility(View.VISIBLE);

        titleTextView.setVisibility(View.GONE);
        timeTextView.setVisibility(View.GONE);
        rectBlackView.setVisibility(View.GONE);
        rectBackgroundView.setVisibility(View.GONE);
    }

    private void startTimer(int i, ConstraintLayout.LayoutParams params2, int minLeft, int n) {
        countDownTimer = new CountDownTimer(i*1000, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTick(long l) {

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rectBlackView.getLayoutParams();

                int minutes = new Date().getMinutes();
                int seconds = new Date().getSeconds();
                if (minutes == 0 && seconds == 0) {
                    setTimeAndProgressBar();
                    cancel();
                } else {
                    params.height = (220 * (minLeft - minutes) / n) * 3;
                    rectBlackView.setLayoutParams(params);

                    updateTimer(l);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFinish() {
                setTimeAndProgressBar();
            }
        };
        countDownTimer.start();
    }

    private void updateTimer(long millisSec) {
        int minutes = (int) millisSec/1000/60;
        int seconds = (int) millisSec/1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }

        timeTextView.setText(minutesString + ":" + secondsString);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getDayStringNew(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.add_item).setVisible(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        } catch (Exception ignored) {

        }
    }
}