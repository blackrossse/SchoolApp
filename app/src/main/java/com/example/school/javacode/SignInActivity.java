package com.example.school.javacode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private TextView loginTextView;
    private EditText nameEditText;
    private EditText mailEditText;
    private EditText passwordEditText;
    private Button registrButton;
    private TextView newAdminTextView;
    private View backgroundProgressBarView;
    private ProgressBar progressBar;

    private View errorView1;
    private View errorView2;
    private View errorView3;

    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private FirebaseAuth auth;

    private boolean loginMode;
    public static boolean isAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginTextView = findViewById(R.id.loginTextView);
        nameEditText = findViewById(R.id.nameEditText);
        mailEditText = findViewById(R.id.mailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registrButton = findViewById(R.id.registrButton);
        newAdminTextView = findViewById(R.id.newAdminTextView);
        backgroundProgressBarView = findViewById(R.id.backgroundProgressBarView);
        progressBar = findViewById(R.id.progressBar);

        errorView1 = findViewById(R.id.errorView1);
        errorView2 = findViewById(R.id.errorView2);
        errorView3 = findViewById(R.id.errorView3);

        nameEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
        mailEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
        passwordEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});

        newAdminTextView.setPaintFlags(
                newAdminTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        usersRef = database.getReference().child("users");

        registrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInOrSignUp(mailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }
        });

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            overridePendingTransition(0, 0); // удаление анимации перехода
        }

        View.OnTouchListener listener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    switch (view.getId()) {
                        case R.id.nameEditText:
                            errorView1.setVisibility(View.INVISIBLE);
                            errorView2.setVisibility(View.INVISIBLE);
                            errorView3.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.mailEditText:
                            errorView1.setVisibility(View.INVISIBLE);
                            errorView2.setVisibility(View.INVISIBLE);
                            errorView3.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.passwordEditText:
                            errorView1.setVisibility(View.INVISIBLE);
                            errorView2.setVisibility(View.INVISIBLE);
                            errorView3.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
                return false;
            }
        };
        nameEditText.setOnTouchListener(listener);
        mailEditText.setOnTouchListener(listener);
        passwordEditText.setOnTouchListener(listener);
    }

    private void signInOrSignUp(String mail, String password) {

        if (loginMode) {
            // Login
            if (mail.equals("") && password.equals("")) {
                errorView2.setVisibility(View.VISIBLE);
                errorView3.setVisibility(View.VISIBLE);
            } else if (mail.equals("")) {
                errorView2.setVisibility(View.VISIBLE);
            } else if (password.length() < 6) {
                errorView3.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Пароль минимум 6 символов",
                            Toast.LENGTH_SHORT).show();
            } else {
                backgroundProgressBarView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                backgroundProgressBarView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SignInActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this, "Пользователя не существует!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        } else {
            // Registration
            if (nameEditText.getText().toString().trim().length() == 0 && mail.equals("") &&
                password.equals("")) {
                errorView1.setVisibility(View.VISIBLE);
                errorView2.setVisibility(View.VISIBLE);
                errorView3.setVisibility(View.VISIBLE);

            } else if (mail.equals("") && password.equals("")) {
                errorView2.setVisibility(View.VISIBLE);
                errorView3.setVisibility(View.VISIBLE);
            } else if (nameEditText.getText().toString().trim().length() == 0) {
                errorView1.setVisibility(View.VISIBLE);
            }
            else if (nameEditText.getText().toString().trim().length() >= 12) {
                errorView1.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Слишком длинное имя", Toast.LENGTH_SHORT).show();
            } else if (mail.equals("")) {
                errorView2.setVisibility(View.VISIBLE);
            } else if (password.length() < 6) {
                errorView3.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Пароль минимум 6 символов",
                        Toast.LENGTH_SHORT).show();
            } else {
                backgroundProgressBarView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    backgroundProgressBarView.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);

                                    createUser(user);

                                    nameEditText.setText("");
                                    mailEditText.setText("");
                                    passwordEditText.setText("");

                                    Toast.makeText(SignInActivity.this,
                                            "Успешная регистрация. Теперь ты можешь войти в аккаунт",
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    backgroundProgressBarView.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(SignInActivity.this, "Почты не существует!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    private void createUser(FirebaseUser firebaseUser) {

        // Подготавливаем данные, которые нужно сохранить в БД
        User user = new User();
        user.setName(nameEditText.getText().toString().trim());
        user.setMail(firebaseUser.getEmail());
        user.setId(firebaseUser.getUid());
        user.setPassword(passwordEditText.getText().toString().trim());
        user.setAdmin("false");

        // В этой части мы вызываем билдер, чтобы установить имя для пользователя.
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(nameEditText.getText().toString().trim());
        firebaseUser.updateProfile(builder.build());

        usersRef.child(firebaseUser.getUid()).setValue(user);
    }

    public void toggleLogin(View view) {
        errorView1.setVisibility(View.INVISIBLE);
        errorView2.setVisibility(View.INVISIBLE);
        errorView3.setVisibility(View.INVISIBLE);

        if (loginMode) {
            loginMode = false;
            newAdminTextView.setVisibility(View.GONE);
            nameEditText.setVisibility(View.VISIBLE);
            registrButton.setText("Регистрация");
            loginTextView.setText("вход");
        } else {
            loginMode = true;
            newAdminTextView.setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.GONE);
            registrButton.setText("войти");
            loginTextView.setText("регистрация");
        }
    }

    public void addNewAdmin(View view) {
        String mail = mailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (mail.equals("") && password.equals("")) {
            errorView2.setVisibility(View.VISIBLE);
            errorView3.setVisibility(View.VISIBLE);
        } else if (mail.equals("")) {
            errorView2.setVisibility(View.VISIBLE);
        } else if (password.length() < 6) {
            errorView3.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Пароль минимум 6 символов",
                    Toast.LENGTH_SHORT).show();
        } else {
            backgroundProgressBarView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = auth.getCurrentUser();

                                usersRef.child(user.getUid()).child("admin").get().
                                        addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                backgroundProgressBarView.setVisibility(View.GONE);
                                                progressBar.setVisibility(View.GONE);

                                                if (task.getResult().getValue().toString().equals("true")) {
                                                    Toast.makeText(SignInActivity.this,
                                                            "Ты уже админ!", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(SignInActivity.this,
                                                            MainActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(SignInActivity.this,
                                                            NewAdminActivity.class);
                                                    intent.putExtra("userName", user.getDisplayName());
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                } else {
                                backgroundProgressBarView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Пользователя не существует!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}