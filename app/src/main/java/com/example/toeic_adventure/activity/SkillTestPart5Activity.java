package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillTestPart5Activity extends AppCompatActivity {
    String skillTestId;
    JSONArray questions;
    JSONObject question, answer;
    JSONArray choices;
    List<String> userAnswer;
    int index = 0;
    boolean isSubmitted;

    TextView tvIndex;
    ImageView ivClose;
    TextView tvQuestion;
    RadioGroup rgAnswer;
    ImageView ivNext;
    ImageView ivPrev;
    RadioButton rbA, rbB, rbC, rbD;
    TextView tvTranscript;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test_part5);

        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer.set(index, "(A)");
            }
        });
        rbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer.set(index, "(B)");
            }
        });
        rbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer.set(index, "(C)");
            }
        });
        rbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer.set(index, "(D)");
            }
        });
        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != 0) {
                    index--;
                    handleNavigateIcon();
                    handleQuestion();
                }
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != questions.length() - 1) {
                    index++;
                    handleNavigateIcon();
                    handleQuestion();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSubmitted) {
                    int correctSentences  = 0;
                    for (int i = 0; i < questions.length(); i++) {
                        try {
                            if (questions.getJSONObject(i).getJSONObject("answer").getString("text").split(" ")[0].equals(userAnswer.get(i))) {
                                correctSentences++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ApiService.apiService.submitSkillTestAnswer(correctSentences, skillTestId, questions.length()).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SkillTestPart5Activity.this, "Submitted answer", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart5Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    isSubmitted = true;
                    handleQuestion();
                }
            }
        });
    }

    private void fetchTest() {
        Intent intent = getIntent();
        skillTestId = intent.getStringExtra("id");
        ApiService.apiService.getSkillTest(skillTestId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonFile = resObj.getJSONObject("jsonFile");
                    String url = jsonFile.getString("url");
                    ApiService.apiService.getSkillTestFile(url).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            try {
                                JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                                questions = resObj.getJSONArray("questions");
                                for (int i = 0; i < questions.length(); i++) {
                                    userAnswer.add("");
                                }
                                handleNavigateIcon();
                                handleQuestion();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SkillTestPart5Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart5Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestPart5Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        rgAnswer = (RadioGroup) findViewById(R.id.rgAnswer);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        rbA =  (RadioButton) findViewById(R.id.rbA);
        rbB = (RadioButton) findViewById(R.id.rbB);
        rbC = (RadioButton) findViewById(R.id.rbC);
        rbD = (RadioButton) findViewById(R.id.rbD);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        userAnswer = new ArrayList<>();
        isSubmitted = false;
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index ).getJSONObject("question");
            answer = questions.getJSONObject(index ).getJSONObject("answer");
            choices = question.getJSONArray("choices");
            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());
            tvQuestion.setText(question.getString("text"));
            rbA.setText(choices.getString(0));
            rbB.setText(choices.getString(1));
            rbC.setText(choices.getString(2));
            rbD.setText(choices.getString(3));

            switch (userAnswer.get(index)) {
                case "":
                    rgAnswer.clearCheck();
                    break;
                case "(A)":
                    rbA.setChecked(true);
                    break;
                case "(B)":
                    rbB.setChecked(true);
                    break;
                case "(C)":
                    rbC.setChecked(true);
                    break;
                case "(D)":
                    rbD.setChecked(true);
                    break;
            }
            if (index == questions.length() - 1) {
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                btnSubmit.setVisibility(View.INVISIBLE);
            }
            if (isSubmitted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation"), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation")));
                }
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setText("Exit");
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                tvTranscript.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNavigateIcon() {
        if (index == 0) {
            ivPrev.setImageDrawable(null);
        } else {
            ivPrev.setImageResource(R.drawable.prev_question);
        }
        if (index == questions.length() - 1) {
            ivNext.setImageDrawable(null);
        } else {
            ivNext.setImageResource(R.drawable.next_question);
        }
    }
}