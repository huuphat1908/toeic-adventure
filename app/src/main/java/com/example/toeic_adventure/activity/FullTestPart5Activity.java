package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FullTestPart5Activity extends AppCompatActivity {
    JSONArray questions;
    JSONObject question, answer;
    JSONArray choices;
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

    int correctSentences = 0;
    int completedSentences = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test_part5);

        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    answer.put("userAnswer", question.getJSONArray("choices").get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    answer.put("userAnswer", question.getJSONArray("choices").get(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    answer.put("userAnswer", question.getJSONArray("choices").get(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    answer.put("userAnswer", question.getJSONArray("choices").get(3));
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
                onSubmit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onSubmit();
        super.onBackPressed();
    }

    private void onSubmit() {
        for (int i = 0; i < questions.length(); i++) {
            try {
                if (questions.getJSONObject(i).getJSONObject("answer").getString("userAnswer")
                        .equals(questions.getJSONObject(i).getJSONObject("answer").getString("text"))) {
                    correctSentences++;
                }
                if (!questions.getJSONObject(i).getJSONObject("answer").getString("userAnswer")
                        .equals("")) {
                    completedSentences++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("part", 5);
        intent.putExtra("questions", questions.toString());
        intent.putExtra("completedSentences", completedSentences);
        intent.putExtra("correctSentences", correctSentences);
        setResult(1, intent);
        finish();
    }

    private void fetchTest() {
        try {
            questions = new JSONArray(getIntent().getStringExtra("questions"));
            handleNavigateIcon();
            handleQuestion();
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
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
        isSubmitted = getIntent().getBooleanExtra("isSubmitted", false);
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

            switch (answer.getString("userAnswer").split(" ")[0]) {
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
            if (isSubmitted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation"), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation")));
                }
                tvTranscript.setVisibility(View.VISIBLE);
                rgAnswer.setClickable(false);
                rbA.setClickable(false);
                rbB.setClickable(false);
                rbC.setClickable(false);
                rbD.setClickable(false);
                rbA.setTextColor(getResources().getColor(R.color.black));
                rbB.setTextColor(getResources().getColor(R.color.black));
                rbC.setTextColor(getResources().getColor(R.color.black));
                rbD.setTextColor(getResources().getColor(R.color.black));
                switch (rgAnswer.getCheckedRadioButtonId()) {
                    case -1:
                        switch (answer.getString("text").split(" ")[0]) {
                            case "(A)":
                                rbA.setTextColor(getResources().getColor(R.color.pink));
                                break;
                            case "(B)":
                                rbB.setTextColor(getResources().getColor(R.color.pink));
                                break;
                            case "(C)":
                                rbC.setTextColor(getResources().getColor(R.color.pink));
                                break;
                            case "(D)":
                                rbD.setTextColor(getResources().getColor(R.color.pink));
                                break;
                        }
                        break;
                    case R.id.rbA:
                        if (!answer.getString("text").split(" ")[0].equals("(A)")) {
                            rbA.setTextColor(getResources().getColor(R.color.pink));
                        } else {
                            rbA.setTextColor(getResources().getColor(R.color.green));
                        }
                        break;
                    case R.id.rbB:
                        if (!answer.getString("text").split(" ")[0].equals("(B)")) {
                            rbB.setTextColor(getResources().getColor(R.color.pink));
                        } else {
                            rbB.setTextColor(getResources().getColor(R.color.green));
                        }
                        break;
                    case R.id.rbC:
                        if (!answer.getString("text").split(" ")[0].equals("(C)")) {
                            rbC.setTextColor(getResources().getColor(R.color.pink));
                        } else {
                            rbC.setTextColor(getResources().getColor(R.color.green));
                        }
                        break;
                    case R.id.rbD:
                        if (!answer.getString("text").split(" ")[0].equals("(D)")) {
                            rbD.setTextColor(getResources().getColor(R.color.pink));
                        } else {
                            rbD.setTextColor(getResources().getColor(R.color.green));
                        }
                        break;
                }
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