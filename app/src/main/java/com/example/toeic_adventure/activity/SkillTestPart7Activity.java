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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.QuestionAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.Answer;
import com.example.toeic_adventure.model.Question;
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
    List<Boolean> isSubmittedList;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    TextView tvIndex;
    ImageView ivClose;
    TextView tvQuestion;
    ImageView ivQuestion;
    ListView lvQuestion;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvTranscript;
    Button btnSubmit;
    ScrollView svQuestion;

    ArrayList<Question> questionList;
    ArrayList<Answer> answerList;
    QuestionAdapter adapter;

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
                if (!isSubmitted) {
                    int correctSentences  = 0;
                    int totalSentences = 0;
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
        lvQuestion = (ListView) findViewById(R.id.lvQuestion);
        questionList = new ArrayList<Question>();
        answerList = new ArrayList<Answer>();
        isSubmittedList = new ArrayList<Boolean>();
        adapter = new QuestionAdapter(
                SkillTestPart7Activity.this,
                R.layout.question_layout_item,
                questionList,
                answerList,
                isSubmittedList
        );
        lvQuestion.setAdapter(adapter);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        svQuestion = (ScrollView) findViewById(R.id.svQuestion);
        tvTranscript = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        tvTranscript.setLayoutParams(params);
        tvTranscript.setBackground(ContextCompat.getDrawable(SkillTestPart7Activity.this, R.drawable.sharp_cardview_bg));
        tvTranscript.setTextColor(getResources().getColor(R.color.black));
        tvTranscript.setVisibility(View.INVISIBLE);
        if (tvTranscript != null) {
            lvQuestion.addFooterView(tvTranscript);
        } else {
            throw new NullPointerException("tvTranscript is null");
        }
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        isSubmitted = false;
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");

            svQuestion.smoothScrollTo(0, 0);
            lvQuestion.smoothScrollToPosition(0);

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

            if (index == questions.length() - 1) {
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                btnSubmit.setVisibility(View.INVISIBLE);
            }
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