package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAuthenticationActivity extends AppCompatActivity {

    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_authentication);

        initView();
        setUpCodeInput();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String redirectFrom = intent.getStringExtra("redirectFrom");
        if (redirectFrom.isEmpty()) {
            ApiService.apiService.sendVerificationEmail(email).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) { }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) { }
            });
        }

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = inputCode1.getText().toString()
                        + inputCode2.getText().toString()
                        + inputCode3.getText().toString()
                        + inputCode4.getText().toString()
                        + inputCode5.getText().toString()
                        + inputCode6.getText().toString();

                ApiService.apiService.verifyEmail(email, code).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterAuthenticationActivity.this, "Verify successfully", Toast.LENGTH_SHORT).show();
                            Intent loginActivityIntent = new Intent(RegisterAuthenticationActivity.this, LoginActivity.class);
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(loginActivityIntent);
                                }
                            }, 1000L);
                        } else {
                           Toast.makeText(RegisterAuthenticationActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(RegisterAuthenticationActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initView() {
        inputCode1 = (EditText) findViewById(R.id.inputCode1);
        inputCode2 = (EditText) findViewById(R.id.inputCode2);
        inputCode3 = (EditText) findViewById(R.id.inputCode3);
        inputCode4 = (EditText) findViewById(R.id.inputCode4);
        inputCode5 = (EditText) findViewById(R.id.inputCode5);
        inputCode6 = (EditText) findViewById(R.id.inputCode6);
        btnVerify = (Button) findViewById(R.id.btnVerify);
    }

    private void setUpCodeInput() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}