package com.example.school.javacode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.school.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeLessonsActivity extends AppCompatActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference itemsRef = database.getReference().child("items");
    DatabaseReference usersRef;

    FirebaseAuth auth;

    private EditText editTextDialog;
    private Spinner spinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_time_lessons);

        usersRef = database.getReference().child("users");
        auth =  FirebaseAuth.getInstance();

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        ViewPagerAdapter adapterViewPager = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapterViewPager);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position) {
                            case 0:
                                tab.setText("Часы");
                                break;
                            case 1:
                                tab.setText("Д/З");
                                break;
                            default:
                                tab.setText("Новости");
                        }
                    }
                }).attach();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.homework_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.add_item:
                FirebaseUser user = auth.getCurrentUser();

                usersRef.child(user.getUid()).child("admin").get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().getValue().toString().equals("false")) {
                            Toast.makeText(TimeLessonsActivity.this,
                                    "Добавлять Д/З доступно только админам!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    TimeLessonsActivity.this, R.style.AlertDialogCustom);
                            LayoutInflater inflater = getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_homework_edit, null);

                            editTextDialog = view.findViewById(R.id.editText);

                            editTextDialog.setFilters(
                                    new InputFilter[]{new InputFilter.LengthFilter(300)});

                            builder.setView(view);
                            builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog alertDialog = builder.create();

                            spinnerDialog = view.findViewById(R.id.spinner);

                            String[] lessons = {"Английский язык", "Литература", "Русский язык", "Алгебра",
                                    "Геометрия", "Биология", "Физика", "Химия", "География", "Общество",
                                    "История", "ОБЖ"}; // 12 предметов

                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(TimeLessonsActivity.this,
                                    R.layout.spinner_item, lessons);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDialog.setAdapter(spinnerAdapter);

                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();

                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (editTextDialog.getText().toString().equals("")) {
                                                Toast.makeText(TimeLessonsActivity.this, "Ты забыл про дз!",
                                                        Toast.LENGTH_SHORT).show();
                                            } else if (editTextDialog.getText().toString().length() == 1) {
                                                Toast.makeText(TimeLessonsActivity.this, "Что ты написал?",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                saveItem();
                                                alertDialog.dismiss();
                                            }

                                        }
                                    });
                        }
                    }
                });

                break;
        }
        return true;
    }

    private void saveItem() {
        Item item = new Item();
        item.setSelectedItem(spinnerDialog.getSelectedItem().toString());
        item.setText(editTextDialog.getText().toString());

        itemsRef.child(spinnerDialog.getSelectedItem().toString()).setValue(item);
    }

}