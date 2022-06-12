package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.QuestionAdapter;
import com.example.toeic_adventure.model.Answer;
import com.example.toeic_adventure.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FullTestPart5Activity extends AppCompatActivity {
    JSONArray questions;
    JSONObject question, answer;
    int index = 0;
    boolean isSubmitted;
    List<Boolean> isSubmittedList;

    TextView tvIndex;
    ImageView ivClose;
    ListView lvQuestion;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvTranscript;
    Button btnSubmit;

    ArrayList<Question> questionList;
    ArrayList<Answer> answerList;
    QuestionAdapter adapter;

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
        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != 0) {
                    if (!answerList.isEmpty()) {
                        try {
                            questions.getJSONObject(index).getJSONObject("answer").put("userAnswer", answerList.get(0).userAnswer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
                    if (!answerList.isEmpty()) {
                        try {
                            questions.getJSONObject(index).getJSONObject("answer").put("userAnswer", answerList.get(0).userAnswer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
        try {
            questions.getJSONObject(index).getJSONObject("answer").put("userAnswer", answerList.get(0).userAnswer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        lvQuestion = (ListView) findViewById(R.id.lvQuestion);
        questionList = new ArrayList<Question>();
        answerList = new ArrayList<Answer>();
        isSubmittedList = new ArrayList<Boolean>();
        adapter = new QuestionAdapter(
                FullTestPart5Activity.this,
                R.layout.question_layout_item,
                questionList,
                answerList,
                isSubmittedList
        );
        lvQuestion.setAdapter(adapter);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        isSubmitted = getIntent().getBooleanExtra("isSubmitted", false);
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");
            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());

            questionList.clear();
            answerList.clear();
            isSubmittedList.clear();
            questionList.add(new Question(question.getString("text"),
                    question.getJSONArray("image"),
                    question.getJSONArray("sound"),
                    question.getJSONArray("choices")));
            answerList.add(new Answer(answer.getString("text"),
                    answer.getString("explanation"),
                    answer.getString("userAnswer")));
            isSubmittedList.add(isSubmitted);
            adapter.notifyDataSetChanged();

            if (isSubmitted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation"), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTranscript.setText(Html.fromHtml(answer.getString("explanation")));
                }
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