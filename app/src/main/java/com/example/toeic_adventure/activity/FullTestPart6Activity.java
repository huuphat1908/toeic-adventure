package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class FullTestPart6Activity extends AppCompatActivity {
    JSONArray questions;
    JSONObject question;
    JSONObject answer;
    int index = 0;
    boolean isSubmitted;
    List<Boolean> isSubmittedList;

    TextView tvIndex;
    ImageView ivClose;
    TextView tvQuestion;
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
        setContentView(R.layout.activity_full_test_part6);

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
                            for (int i = 0; i < questions.getJSONObject(index).getJSONArray("childs").length(); i++) {
                                questions.getJSONObject(index).getJSONArray("childs").getJSONObject(i).getJSONObject("answer")
                                        .put("userAnswer", answerList.get(i).userAnswer);
                            }
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
                            for (int i = 0; i < questions.getJSONObject(index).getJSONArray("childs").length(); i++) {
                                questions.getJSONObject(index).getJSONArray("childs").getJSONObject(i).getJSONObject("answer")
                                        .put("userAnswer", answerList.get(i).userAnswer);
                            }
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
            for (int i = 0; i < questions.getJSONObject(index).getJSONArray("childs").length(); i++) {
                questions.getJSONObject(index).getJSONArray("childs").getJSONObject(i).getJSONObject("answer")
                        .put("userAnswer", answerList.get(i).userAnswer);
            }
            for (int i = 0; i < questions.length(); i++) {
                for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                    JSONObject currAnswer = questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer");
                    if (currAnswer.getString("userAnswer").equals(currAnswer.getString("text"))) {
                        correctSentences++;
                    }
                    if (!currAnswer.getString("userAnswer").equals("")) {
                        completedSentences++;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FullTestPart6Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        intent.putExtra("part", 3);
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
        lvQuestion = (ListView) findViewById(R.id.lvQuestion);
        questionList = new ArrayList<Question>();
        answerList = new ArrayList<Answer>();
        isSubmittedList = new ArrayList<Boolean>();
        adapter = new QuestionAdapter(
                FullTestPart6Activity.this,
                R.layout.question_layout_item,
                questionList,
                answerList,
                isSubmittedList
        );
        lvQuestion.setAdapter(adapter);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvTranscript = new TextView(this);
        tvTranscript.setBackground(ContextCompat.getDrawable(FullTestPart6Activity.this, R.drawable.sharp_cardview_bg));
        tvTranscript.setTextColor(getResources().getColor(R.color.black));
        tvTranscript.setVisibility(View.INVISIBLE);
        if (tvTranscript != null) {
            lvQuestion.addFooterView(tvTranscript);
        } else {
            throw new NullPointerException("tvTranscript is null");
        }
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        isSubmitted = getIntent().getBooleanExtra("isSubmitted", false);isSubmitted = getIntent().getBooleanExtra("isSubmitted", false);
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");

            lvQuestion.smoothScrollToPosition(0);

            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvQuestion.setText(Html.fromHtml(question.getString("text"), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvQuestion.setText(Html.fromHtml(question.getString("text")));
            }

            questionList.clear();
            answerList.clear();
            isSubmittedList.clear();
            for (int i = 0; i < questions.getJSONObject(index).getJSONArray("childs").length(); i++) {
                isSubmittedList.add(isSubmitted);
                JSONObject curChildQuestion = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(i).getJSONObject("question");
                JSONObject curChildAnswer = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(i).getJSONObject("answer");
                questionList.add(new Question(curChildQuestion.getString("text"),
                        curChildQuestion.getJSONArray("image"),
                        curChildQuestion.getJSONArray("sound"),
                        curChildQuestion.getJSONArray("choices")));
                answerList.add(new Answer(curChildAnswer.getString("text"),
                        curChildAnswer.getString("explanation"),
                        curChildAnswer.getString("userAnswer")));
            }
            adapter.notifyDataSetChanged();

            if (isSubmitted) {
                String transcript = "<b>Transcript</b><br />" ;
                for (int i = 0; i < questionList.size(); i++) {
                    transcript += "<b>" + answerList.get(i).text + ":</b> " + answerList.get(i).explanation + "<br />";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTranscript.setText(Html.fromHtml(transcript, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTranscript.setText(Html.fromHtml(transcript));
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