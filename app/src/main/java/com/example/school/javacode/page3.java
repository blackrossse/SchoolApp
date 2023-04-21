package com.example.school.javacode;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.school.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page3.
     */
    // TODO: Rename and change types and number of parameters
    public static page3 newInstance(String param1, String param2) {
        page3 fragment = new page3();
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


    ConstraintLayout cl;
    RecyclerView recyclerView;
    Button button;
    Button button2;
    EditText textEditText;
    Button showButton;
    ProgressBar progressBar;

    ArrayList<News> news;
    NewsRecyclerViewAdapter adapter;

    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference newsRef;
    DatabaseReference usersRef;

    Calendar calendar;
    SimpleDateFormat df;

    View viewRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        viewRoot = inflater.inflate(R.layout.fragment_page3, container, false);

        cl = viewRoot.findViewById(R.id.internalConstraintLayout);

        recyclerView = viewRoot.findViewById(R.id.recyclerView);
        textEditText = viewRoot.findViewById(R.id.textEditText);
        button = viewRoot.findViewById(R.id.button);
        button2 = viewRoot.findViewById(R.id.button2);
        showButton = viewRoot.findViewById(R.id.showButton);
        progressBar = viewRoot.findViewById(R.id.progressBar);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        newsRef = database.getReference().child("news");
        usersRef = database.getReference().child("users");

        news = new ArrayList<>();
        adapter = new NewsRecyclerViewAdapter(getActivity(), news);
        recyclerView.setAdapter(adapter);

        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("dd.MM в HH:mm");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Пустое поле!", Toast.LENGTH_SHORT).show();
                } else if (textEditText.getText().toString().length() < 20) {
                    Toast.makeText(getContext(), "Текст слишком короткий!", Toast.LENGTH_SHORT).show();
                } else {
                    News itemNew = new News();
                    itemNew.setUserName(auth.getCurrentUser().getDisplayName());
                    itemNew.setTimeAndDate(df.format(calendar.getTime()));
                    itemNew.setText(textEditText.getText().toString());

                    newsRef.push().setValue(itemNew);
                    textEditText.setText("");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.trans);
                Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.trans2);

                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        button.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);
                        textEditText.setVisibility(View.GONE);

                        showButton.setVisibility(View.VISIBLE);
                        cl.startAnimation(anim2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                cl.startAnimation(anim);

                ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
                ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, 170);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                        recyclerView.requestLayout();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.child(auth.getCurrentUser().getUid()).child("admin").get().
                        addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.getResult().getValue().equals("false")) {
                                    Toast.makeText(getContext(),
                                            "Добавлять новости доступно только админам!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.trans);
                                    Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.trans2);

                                    anim.setDuration(250);

                                    anim.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            button.setVisibility(View.VISIBLE);
                                            button2.setVisibility(View.VISIBLE);
                                            textEditText.setVisibility(View.VISIBLE);

                                            showButton.setVisibility(View.GONE);
                                            cl.startAnimation(anim2);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });

                                    cl.startAnimation(anim);

                                    ConstraintLayout.LayoutParams params =
                                            (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
                                    ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, 270);
                                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator)
                                        {
                                            params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                                            recyclerView.requestLayout();
                                        }
                                    });
                                    animator.setDuration(300);
                                    animator.start();
                                }
                            }
                        });
            }
        });

        ChildEventListener newsChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                News n = snapshot.getValue(News.class);
                news.add(0, n);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        newsRef.addChildEventListener(newsChildListener);

        return viewRoot;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.add_item).setVisible(false);
    }
}