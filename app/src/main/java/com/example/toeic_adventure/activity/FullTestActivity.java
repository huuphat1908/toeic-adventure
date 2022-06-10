package com.example.toeic_adventure.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTestActivity extends AppCompatActivity {
    String fullTestId;
    Toolbar myToolbar;
    TextView tvPart1, tvPart2, tvPart3, tvPart4, tvPart5, tvPart6, tvPart7;
    JSONArray questions;
    JSONArray part1 = new JSONArray();
    JSONArray part2 = new JSONArray();
    JSONArray part3 = new JSONArray();
    JSONArray part4 = new JSONArray();
    JSONArray part5 = new JSONArray();
    JSONArray part6 = new JSONArray();
    JSONArray part7 = new JSONArray();
    Button btnSubmit;
    int score = 0;
    boolean isSubmitted = false;
    int totalSentencesPart1 = 0;
    int totalSentencesPart2 = 0;
    int totalSentencesPart3 = 0;
    int totalSentencesPart4 = 0;
    int totalSentencesPart5 = 0;
    int totalSentencesPart6 = 0;
    int totalSentencesPart7 = 0;
    int completedSentencesPart1 = 0;
    int completedSentencesPart2 = 0;
    int completedSentencesPart3 = 0;
    int completedSentencesPart4 = 0;
    int completedSentencesPart5 = 0;
    int completedSentencesPart6 = 0;
    int completedSentencesPart7 = 0;
    int correctSentencesPart1 = 0;
    int correctSentencesPart2 = 0;
    int correctSentencesPart3 = 0;
    int correctSentencesPart4 = 0;
    int correctSentencesPart5 = 0;
    int correctSentencesPart6 = 0;
    int correctSentencesPart7 = 0;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == 1) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        try {
                            switch (intent.getIntExtra("part", 0)) {
                                case 1:
                                    score += intent.getIntExtra("correctSentences", 0);
                                    completedSentencesPart1 = intent.getIntExtra("completedSentences", 0);
                                    correctSentencesPart1 = intent.getIntExtra("correctSentences", 0);
                                    part1 = new JSONArray(intent.getStringExtra("questions"));
                                    tvPart1.setText("Part 1\nCompleted: " + String.valueOf(completedSentencesPart1)
                                            + "/" + String.valueOf(totalSentencesPart1));
                                    break;
                                case 2:
                                    part2 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                case 3:
                                    part3 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                case 4:
                                    part4 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                case 5:
                                    part5 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                case 6:
                                    part6 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                case 7:
                                    part7 = new JSONArray(intent.getStringExtra("questions"));
                                    break;
                                default:
                                    Toast.makeText(FullTestActivity.this, "Arsenal", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(FullTestActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test);

        initView();
        fetchTest();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullTestPart1Intent = new Intent(FullTestActivity.this, FullTestPart1Activity.class);
                fullTestPart1Intent.putExtra("questions", part1.toString());
                fullTestPart1Intent.putExtra("isSubmitted", isSubmitted);
                activityResultLauncher.launch(fullTestPart1Intent);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSubmitted = true;
                tvPart1.setText("Part 1\nScore: " + String.valueOf(correctSentencesPart1)
                        + "/" + String.valueOf(totalSentencesPart1));
            }
        });
    }

    private void initView() {
        myToolbar = findViewById(R.id.toolbar);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);
        tvPart1 = (TextView) findViewById(R.id.tvPart1);
        tvPart2 = (TextView) findViewById(R.id.tvPart2);
        tvPart3 = (TextView) findViewById(R.id.tvPart3);
        tvPart4 = (TextView) findViewById(R.id.tvPart4);
        tvPart5 = (TextView) findViewById(R.id.tvPart5);
        tvPart6 = (TextView) findViewById(R.id.tvPart6);
        tvPart7 = (TextView) findViewById(R.id.tvPart7);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void fetchTest() {
        Intent intent = getIntent();
        fullTestId = intent.getStringExtra("id");
        ApiService.apiService.getFullTest(fullTestId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonFile = resObj.getJSONObject("jsonFile");
                    String url = jsonFile.getString("url");
                    ApiService.apiService.getFullTestFile(url).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            try {
                                JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                                questions = resObj.getJSONArray("questions");
                                splitQuestions();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(FullTestActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(FullTestActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(FullTestActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void splitQuestions() {
        for (int i = 0; i < questions.length(); i++) {
            try {
                switch (questions.getJSONObject(i).getInt("part")) {
                    case 1:
                        questions.getJSONObject(i).getJSONObject("answer").put("userAnswer", "");
                        totalSentencesPart1++;
                        part1.put(questions.getJSONObject(i));
                        break;
                    case 2:
                        questions.getJSONObject(i).getJSONObject("answer").put("userAnswer", "");
                        totalSentencesPart2++;
                        part2.put(questions.getJSONObject(i));
                        break;
                    case 3:
                        for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                            questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer").put("userAnswer", "");
                            totalSentencesPart3++;
                        }
                        part3.put(questions.getJSONObject(i));
                        break;
                    case 4:
                        for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                            questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer").put("userAnswer", "");
                            totalSentencesPart4++;
                        }
                        part4.put(questions.getJSONObject(i));
                        break;
                    case 5:
                        questions.getJSONObject(i).getJSONObject("answer").put("userAnswer", "");
                        totalSentencesPart5++;
                        part5.put(questions.getJSONObject(i));
                        break;
                    case 6:
                        for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                            questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer").put("userAnswer", "");
                            totalSentencesPart6++;
                        }
                        part6.put(questions.getJSONObject(i));
                        break;
                    case 7:
                        for (int j = 0; j < questions.getJSONObject(i).getJSONArray("childs").length(); j++) {
                            questions.getJSONObject(i).getJSONArray("childs").getJSONObject(j).getJSONObject("answer").put("userAnswer", "");
                            totalSentencesPart7++;
                        }
                        part7.put(questions.getJSONObject(i));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}