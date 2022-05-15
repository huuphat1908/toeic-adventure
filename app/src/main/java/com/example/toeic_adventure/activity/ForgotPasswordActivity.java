package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etNewPassword;
    Button btnConfirm;
    TextView tvLogin;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initView();

        etNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etNewPassword.getRight() - etNewPassword.getPaddingRight() - etNewPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        int selection = etNewPassword.getSelectionEnd();
                        if (passwordVisible) {
                            etNewPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_closing_eye_icon, 0);
                            etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            etNewPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.et_opening_eye_icon, 0);
                            etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        etNewPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                } else if (newPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "New password is required", Toast.LENGTH_SHORT).show();
                } else if (newPassword.length() <= 7) {
                    Toast.makeText(ForgotPasswordActivity.this,  "New password is at least  8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    ApiService.apiService.forgotPassword(email).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                Intent forgotPasswordAuthenticationIntent = new Intent(ForgotPasswordActivity.this, ForgotPasswordAuthenticationActivity.class);
                                forgotPasswordAuthenticationIntent.putExtra("email", email);
                                forgotPasswordAuthenticationIntent.putExtra("newPassword", newPassword);
                                startActivity(forgotPasswordAuthenticationIntent);
                            } else {
                                try {
                                    JSONObject errorObj = new JSONObject(response.errorBody().string());
                                    String message = errorObj.getString("message");
                                    Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(ForgotPasswordActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void initView() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
    }
}