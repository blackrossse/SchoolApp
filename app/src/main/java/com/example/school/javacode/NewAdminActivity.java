package com.example.school.javacode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;

import javax.mail.internet.AddressException;

public class NewAdminActivity extends AppCompatActivity {

    private TextView goToMainTextView;

    private EditText nameEditText;
    private EditText surnameEditText;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);

        goToMainTextView = findViewById(R.id.goToMainTextView);
        goToMainTextView.setPaintFlags(
                goToMainTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        goToMainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewAdminActivity.this, MainActivity.class));
            }
        });

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
    }


    public void sendEmail(View view) throws AddressException {
        /* Высылается заявка на Gmail разработчику(мне), о том, чтобы стать админом */

        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.AlertDialogCustom);

        View v = getLayoutInflater().inflate(R.layout.dialog_send_message, null);

        nameEditText = v.findViewById(R.id.nameEditText);
        surnameEditText = v.findViewById(R.id.surnameEditText);

        nameEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
        surnameEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});

        builder.setView(v);
        builder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
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
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nameEditText.getText().toString().equals("") ||
                            surnameEditText.getText().toString().equals("")) {
                            Toast.makeText(NewAdminActivity.this, "Заполни все поля!",
                                    Toast.LENGTH_SHORT).show();
                        } else if (nameEditText.getText().toString().length() <= 2 ||
                                    surnameEditText.getText().toString().length() <= 2) {
                            Toast.makeText(NewAdminActivity.this, "Неверно!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            sendMessage(nameEditText.getText().toString(),
                                    surnameEditText.getText().toString());
                            Toast.makeText(NewAdminActivity.this, "Отправлено!",
                                    Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });
    }

    private void sendMessage(String name, String surname) {
        String mail = "nicknickkorol@gmail.com";
        String password = "bxtlraygttqvaxsu";

        String title = "--- ЗАЯВКА СТАТЬ АДМИНОМ ---";
        String text = "Никнейм отправителя: " + userName + "\nИмя: " + name + "\nФамилия: "
                        + surname;

        new Thread() {
            public void run() {
                try {
                    MailSenderClass sender = new MailSenderClass(mail, password);
                    sender.sendMail(title, text, mail, mail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}