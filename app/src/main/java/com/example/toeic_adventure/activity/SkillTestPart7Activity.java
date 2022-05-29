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
import android.widget.LinearLayout;
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

public class SkillTestPart7Activity extends AppCompatActivity {
    String skillTestId;
    JSONArray questions;
    JSONObject question;
    JSONObject answer;
    int index = 0;
    boolean isSubmitted;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    TextView tvIndex;
    ImageView ivClose;
    TextView tvQuestion;
    ImageView ivQuestion;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvTranscript;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test_part7);

        config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    int totalSentences = 0;
                    try {
                        for (int i = 0; i < questions.length(); i++) {
                            for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                                JSONObject currAnswer = questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer");
                                if (currAnswer.getString("userAnswer").equals(currAnswer.getString("text"))) {
                                    correctSentences++;
                                }
                                totalSentences++;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SkillTestPart7Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    ApiService.apiService.submitSkillTestAnswer(correctSentences, skillTestId, totalSentences).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SkillTestPart7Activity.this, "Submitted answer", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart7Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
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
                                    for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                                        questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer").put("userAnswer", "");
                                    }
                                }
                                handleNavigateIcon();
                                handleQuestion();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SkillTestPart7Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart7Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestPart7Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        isSubmitted = false;
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");

            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());

            if (question.getString("text").equals("")) {
                LinearLayout.LayoutParams tvQuestionParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0
                );
                tvQuestion.setLayoutParams(tvQuestionParam);
            } else {
                LinearLayout.LayoutParams tvQuestionParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                tvQuestion.setLayoutParams(tvQuestionParam);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvQuestion.setText(Html.fromHtml(question.getString("text"), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvQuestion.setText(Html.fromHtml(question.getString("text")));
                }
            }
            if (question.getJSONArray("image").length() == 0) {
                LinearLayout.LayoutParams ivQuestionParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0
                );
                ivQuestion.setLayoutParams(ivQuestionParam);
            } else {
                LinearLayout.LayoutParams ivQuestionParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                ivQuestion.setLayoutParams(ivQuestionParam);
                imageLoader.displayImage(question.getJSONArray("image").getJSONObject(0).getString("url"), ivQuestion);
            }

            if (index == questions.length() - 1) {
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                btnSubmit.setVisibility(View.INVISIBLE);
            }
            if (isSubmitted) {
                String transcript = "<b>Transcript</b><br />" ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTranscript.setText(Html.fromHtml(transcript, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTranscript.setText(Html.fromHtml(transcript));
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