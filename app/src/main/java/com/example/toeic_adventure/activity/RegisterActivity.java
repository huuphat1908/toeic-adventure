package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvLogin;
    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirmation;

    private boolean passwordVisible;
    private boolean passwordConfirmationVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
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

        etPasswordConfirmation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPasswordConfirmation.getRight() - etPasswordConfirmation.getPaddingRight() - etPasswordConfirmation.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int selection = etPasswordConfirmation.getSelectionEnd();
                        if (passwordConfirmationVisible) {
                            etPasswordConfirmation.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_closing_eye_icon, 0);
                            etPasswordConfirmation.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordConfirmationVisible = false;
                        } else {
                            etPasswordConfirmation.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_opening_eye_icon, 0);
                            etPasswordConfirmation.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordConfirmationVisible = true;
                        }
                        etPasswordConfirmation.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirmation = etPasswordConfirmation.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                } else if (passwordConfirmation.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password confirmation is required", Toast.LENGTH_SHORT).show();
                } else if (password.length() <= 7) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordConfirmation)) {
                    Toast.makeText(RegisterActivity.this, "Password and password confirmation must be the same", Toast.LENGTH_SHORT).show();
                } else {
                    ApiService.apiService.register(email, password).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Register successfullly", Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                                    String token = resObj.getString("token");
                                    SharedPreferences.Editor editor = MainActivity.localStorage.edit();
                                    editor.putString("token", token);
                                    editor.commit();
                                } catch (JSONException e) {
                                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                Intent registerAuthenticationIntent = new Intent(RegisterActivity.this, RegisterAuthenticationActivity.class);
                                registerAuthenticationIntent.putExtra("email", email);
                                registerAuthenticationIntent.putExtra("redirectFrom", "Register");
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(registerAuthenticationIntent);
                                    }
                                }, 750L);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Email is already taken", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
    }
}