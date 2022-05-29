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

public class SkillTestPart3Activity extends AppCompatActivity {
    String skillTestId;
    JSONArray questions;
    JSONObject question, childQuestion1, childQuestion2, childQuestion3;
    JSONObject answer, childAnswer1, childAnswer2, childAnswer3;
    JSONArray choices1, choices2, choices3;
    int index = 0;
    boolean isSubmitted;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    TextView tvIndex;
    ImageView ivClose;
    ImageView ivQuestion;
    TextView tvQuestion1, tvQuestion2, tvQuestion3;
    RadioGroup rgAnswer1, rgAnswer2, rgAnswer3;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvCurrentTime;
    TextView tvTotalDuration;
    SeekBar sbAudio;
    ImageView ivPlayPause;
    RadioButton rbA1, rbB1, rbC1, rbD1;
    RadioButton rbA2, rbB2, rbC2, rbD2;
    RadioButton rbA3, rbB3, rbC3, rbD3;
    TextView tvTranscript;
    Button btnSubmit;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test_part3);

        config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
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
        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    ivPlayPause.setImageResource(R.drawable.play_audio);
                } else {
                    mediaPlayer.start();
                    ivPlayPause.setImageResource(R.drawable.pause_audio);
                    updateSeekBar();
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
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                    prepareMediaPlayer();
                    if (mediaPlayer.isPlaying()) {
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        ivPlayPause.setImageResource(R.drawable.play_audio);
                    } else {
                        mediaPlayer.start();
                        ivPlayPause.setImageResource(R.drawable.pause_audio);
                        updateSeekBar();
                    }
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
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                    prepareMediaPlayer();
                    if (mediaPlayer.isPlaying()) {
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        ivPlayPause.setImageResource(R.drawable.play_audio);
                    } else {
                        mediaPlayer.start();
                        ivPlayPause.setImageResource(R.drawable.pause_audio);
                        updateSeekBar();
                    }
                }
            }
        });
        sbAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                sbAudio.setSecondaryProgress(i);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sbAudio.setProgress(0);
                ivPlayPause.setImageResource(R.drawable.play_audio);
                tvCurrentTime.setText("0:00");
                tvTotalDuration.setText("0:00");
                mediaPlayer.reset();
                prepareMediaPlayer();
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
                        Toast.makeText(SkillTestPart3Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    ApiService.apiService.submitSkillTestAnswer(correctSentences, skillTestId, totalSentences).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SkillTestPart3Activity.this, "Submitted answer", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart3Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
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
                                prepareMediaPlayer();
                                if (mediaPlayer.isPlaying()) {
                                    handler.removeCallbacks(updater);
                                    mediaPlayer.pause();
                                    ivPlayPause.setImageResource(R.drawable.play_audio);
                                } else {
                                    mediaPlayer.start();
                                    ivPlayPause.setImageResource(R.drawable.pause_audio);
                                    updateSeekBar();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SkillTestPart3Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart3Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestPart3Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        tvQuestion1 = (TextView) findViewById(R.id.tvQuestion1);
        tvQuestion2 = (TextView) findViewById(R.id.tvQuestion2);
        tvQuestion3 = (TextView) findViewById(R.id.tvQuestion3);
        rgAnswer1 = (RadioGroup) findViewById(R.id.rgAnswer1);
        rgAnswer2 = (RadioGroup) findViewById(R.id.rgAnswer2);
        rgAnswer3 = (RadioGroup) findViewById(R.id.rgAnswer3);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        sbAudio = (SeekBar) findViewById(R.id.sbAudio);
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
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mediaPlayer = new MediaPlayer();
        sbAudio.setMax(100);
        isSubmitted = false;
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index).getJSONObject("question");
            answer = questions.getJSONObject(index).getJSONObject("answer");

            childQuestion1 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(0).getJSONObject("question");
            childQuestion2 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(1).getJSONObject("question");
            childQuestion3 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(2).getJSONObject("question");
            childAnswer1 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(0).getJSONObject("answer");
            childAnswer2 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(1).getJSONObject("answer");
            childAnswer3 = questions.getJSONObject(index).getJSONArray("childs").getJSONObject(2).getJSONObject("answer");

            choices1 = childQuestion1.getJSONArray("choices");
            choices2 = childQuestion2.getJSONArray("choices");
            choices3 = childQuestion3.getJSONArray("choices");
            int indexTitle = index + 1;
            tvIndex.setText(indexTitle + "/" + questions.length());
            imageLoader.displayImage(question.getJSONArray("image").length() != 0 ?
                    question.getJSONArray("image").getJSONObject(0).getString("url")
                    : null, ivQuestion);
            if (question.getJSONArray("image").length() == 0) {
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.0f
                );
                ivQuestion.setLayoutParams(param);
            } else {
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.0f
                );
                ivQuestion.setLayoutParams(param);
            }
            tvQuestion1.setText("Câu 1: " + childQuestion1.getString("text"));
            rbA1.setText(choices1.getString(0));
            rbB1.setText(choices1.getString(1));
            rbC1.setText(choices1.getString(2));
            rbD1.setText(choices1.getString(3));

            tvQuestion2.setText("Câu 2: " + childQuestion2.getString("text"));
            rbA2.setText(choices2.getString(0));
            rbB2.setText(choices2.getString(1));
            rbC2.setText(choices2.getString(2));
            rbD2.setText(choices2.getString(3));

            tvQuestion3.setText("Câu 3: " + childQuestion3.getString("text"));
            rbA3.setText(choices3.getString(0));
            rbB3.setText(choices3.getString(1));
            rbC3.setText(choices3.getString(2));
            rbD3.setText(choices3.getString(3));

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

    private void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(question.getJSONArray("sound").getJSONObject(0).getString("url"));
            mediaPlayer.prepare();
            tvTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        } catch (Exception e) {
            Toast.makeText(SkillTestPart3Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Error", e.getMessage());
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            tvCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            sbAudio.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondsToTimer(long milliSeconds) {
        String timerString = "";
        String secondsString;
        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            timerString = hours + ":";
        }
        if(seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }
}