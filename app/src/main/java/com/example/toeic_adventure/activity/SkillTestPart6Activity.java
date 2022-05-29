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

public class SkillTestPart6Activity extends AppCompatActivity {
    String skillTestId;
    JSONArray questions;
    JSONObject question, childQuestion1, childQuestion2, childQuestion3, childQuestion4;
    JSONObject answer, childAnswer1, childAnswer2, childAnswer3, childAnswer4;
    JSONArray choices1, choices2, choices3, choices4;
    int index = 0;
    boolean isSubmitted;

    TextView tvIndex;
    ImageView ivClose;
    TextView tvQuestion;
    TextView tvQuestion1, tvQuestion2, tvQuestion3, tvQuestion4;
    RadioGroup rgAnswer1, rgAnswer2, rgAnswer3, rgAnswer4;
    ImageView ivNext;
    ImageView ivPrev;
    RadioButton rbA1, rbB1, rbC1, rbD1;
    RadioButton rbA2, rbB2, rbC2, rbD2;
    RadioButton rbA3, rbB3, rbC3, rbD3;
    RadioButton rbA4, rbB4, rbC4, rbD4;
    TextView tvTranscript;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test_part6);

        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rbA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer1.put("userAnswer", rbA1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer1.put("userAnswer", rbB1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer1.put("userAnswer", rbC1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer1.put("userAnswer", rbD1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rbA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer2.put("userAnswer", rbA2.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer2.put("userAnswer", rbB2.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer2.put("userAnswer", rbC2.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer2.put("userAnswer", rbD2.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rbA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer3.put("userAnswer", rbA3.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer3.put("userAnswer", rbB3.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer3.put("userAnswer", rbC3.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer3.put("userAnswer", rbD3.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rbA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer4.put("userAnswer", rbA4.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer4.put("userAnswer", rbB4.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer4.put("userAnswer", rbC4.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    childAnswer4.put("userAnswer", rbD4.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        Toast.makeText(SkillTestPart6Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    ApiService.apiService.submitSkillTestAnswer(correctSentences, skillTestId, totalSentences).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SkillTestPart6Activity.this, "Submitted answer", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart6Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(SkillTestPart6Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart6Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestPart6Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvQuestion1 = (TextView) findViewById(R.id.tvQuestion1);
        tvQuestion2 = (TextView) findViewById(R.id.tvQuestion2);
        tvQuestion3 = (TextView) findViewById(R.id.tvQuestion3);
        tvQuestion4 = (TextView) findViewById(R.id.tvQuestion4);
        rgAnswer1 = (RadioGroup) findViewById(R.id.rgAnswer1);
        rgAnswer2 = (RadioGroup) findViewById(R.id.rgAnswer2);
        rgAnswer3 = (RadioGroup) findViewById(R.id.rgAnswer3);
        rgAnswer4 = (RadioGroup) findViewById(R.id.rgAnswer4);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        rbA1 =  (RadioButton) findViewById(R.id.rbA1);
        rbB1 = (RadioButton) findViewById(R.id.rbB1);
        rbC1 = (RadioButton) findViewById(R.id.rbC1);
        rbD1 = (RadioButton) findViewById(R.id.rbD1);
        rbA2 =  (RadioButton) findViewById(R.id.rbA2);
        rbB2 = (RadioButton) findViewById(R.id.rbB2);
        rbC2 = (RadioButton) findViewById(R.id.rbC2);
        rbD2 = (RadioButton) findViewById(R.id.rbD2);
        rbA3 =  (RadioButton) findViewById(R.id.rbA3);
        rbB3 = (RadioButton) findViewById(R.id.rbB3);
        rbC3 = (RadioButton) findViewById(R.id.rbC3);
        rbD3 = (RadioButton) findViewById(R.id.rbD3);
        rbA4 =  (RadioButton) findViewById(R.id.rbA4);
        rbB4 = (RadioButton) findViewById(R.id.rbB4);
        rbC4 = (RadioButton) findViewById(R.id.rbC4);
        rbD4 = (RadioButton) findViewById(R.id.rbD4);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        isSubmitted = false;
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");

            childQuestion1 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(0).getJSONObject("question");
            childQuestion2 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(1).getJSONObject("question");
            childQuestion3 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(2).getJSONObject("question");
            childQuestion4 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(3).getJSONObject("question");
            childAnswer1 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(0).getJSONObject("answer");
            childAnswer2 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(1).getJSONObject("answer");
            childAnswer3 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(2).getJSONObject("answer");
            childAnswer4 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(3).getJSONObject("answer");

            choices1 = childQuestion1.getJSONArray("choices");
            choices2 = childQuestion2.getJSONArray("choices");
            choices3 = childQuestion3.getJSONArray("choices");
            choices4 = childQuestion4.getJSONArray("choices");
            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvQuestion.setText(Html.fromHtml(question.getString("text"), Html.FROM_HTML_MODE_COMPACT));
                tvQuestion1.setText(Html.fromHtml("Câu 1: " + childQuestion1.getString("text"), Html.FROM_HTML_MODE_COMPACT));
                tvQuestion2.setText(Html.fromHtml("Câu 2: " + childQuestion2.getString("text"), Html.FROM_HTML_MODE_COMPACT));
                tvQuestion3.setText(Html.fromHtml("Câu 3: " + childQuestion3.getString("text"), Html.FROM_HTML_MODE_COMPACT));
                tvQuestion4.setText(Html.fromHtml("Câu 4: " + childQuestion4.getString("text"), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvQuestion.setText(Html.fromHtml(question.getString("text")));
                tvQuestion1.setText(Html.fromHtml("Câu 1: " + childQuestion1.getString("text")));
                tvQuestion2.setText(Html.fromHtml("Câu 2: " + childQuestion2.getString("text")));
                tvQuestion3.setText(Html.fromHtml("Câu 3: " + childQuestion3.getString("text")));
                tvQuestion4.setText(Html.fromHtml("Câu 4: " + childQuestion4.getString("text")));
            }
            rbA1.setText(choices1.getString(0));
            rbB1.setText(choices1.getString(1));
            rbC1.setText(choices1.getString(2));
            rbD1.setText(choices1.getString(3));

            rbA2.setText(choices2.getString(0));
            rbB2.setText(choices2.getString(1));
            rbC2.setText(choices2.getString(2));
            rbD2.setText(choices2.getString(3));

            rbA3.setText(choices3.getString(0));
            rbB3.setText(choices3.getString(1));
            rbC3.setText(choices3.getString(2));
            rbD3.setText(choices3.getString(3));

            rbA4.setText(choices4.getString(0));
            rbB4.setText(choices4.getString(1));
            rbC4.setText(choices4.getString(2));
            rbD4.setText(choices4.getString(3));

            switch (childAnswer1.getString("userAnswer").split(" ")[0]) {
                case "":
                    rgAnswer1.clearCheck();
                    break;
                case "(A)":
                    rbA1.setChecked(true);
                    break;
                case "(B)":
                    rbB1.setChecked(true);
                    break;
                case "(C)":
                    rbC1.setChecked(true);
                    break;
                case "(D)":
                    rbD1.setChecked(true);
                    break;
            }
            switch (childAnswer2.getString("userAnswer").split(" ")[0]) {
                case "":
                    rgAnswer2.clearCheck();
                    break;
                case "(A)":
                    rbA2.setChecked(true);
                    break;
                case "(B)":
                    rbB2.setChecked(true);
                    break;
                case "(C)":
                    rbC2.setChecked(true);
                    break;
                case "(D)":
                    rbD2.setChecked(true);
                    break;
            }
            switch (childAnswer3.getString("userAnswer").split(" ")[0]) {
                case "":
                    rgAnswer3.clearCheck();
                    break;
                case "(A)":
                    rbA3.setChecked(true);
                    break;
                case "(B)":
                    rbB3.setChecked(true);
                    break;
                case "(C)":
                    rbC3.setChecked(true);
                    break;
                case "(D)":
                    rbD3.setChecked(true);
                    break;
            }
            switch (childAnswer4.getString("userAnswer").split(" ")[0]) {
                case "":
                    rgAnswer4.clearCheck();
                    break;
                case "(A)":
                    rbA4.setChecked(true);
                    break;
                case "(B)":
                    rbB4.setChecked(true);
                    break;
                case "(C)":
                    rbC4.setChecked(true);
                    break;
                case "(D)":
                    rbD4.setChecked(true);
                    break;
            }

            if (index == questions.length() - 1) {
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                btnSubmit.setVisibility(View.INVISIBLE);
            }
            if (isSubmitted) {
                String transcript = "<b>Transcript</b><br />" ;
                transcript += childAnswer1.getString("text") + ": " + childAnswer1.getString("explanation") + "<br />";
                transcript += childAnswer2.getString("text") + ": " + childAnswer2.getString("explanation") + "<br />";
                transcript += childAnswer3.getString("text") + ": " + childAnswer3.getString("explanation") + "<br />";
                transcript += childAnswer4.getString("text") + ": " + childAnswer4.getString("explanation");
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