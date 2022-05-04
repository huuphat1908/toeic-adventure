package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toeic_adventure.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister;
    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Log.d("Login", email + " " + password);
            }
        });
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getPaddingRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int selection = etPassword.getSelectionEnd();
                        if (passwordVisible) {
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_closing_eye_icon, 0);
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_opening_eye_icon, 0);
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initView() {
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }
}