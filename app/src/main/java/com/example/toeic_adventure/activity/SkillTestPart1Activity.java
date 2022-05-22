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

public class SkillTestPart1Activity extends AppCompatActivity {
    String skillTestId;
    JSONArray questions;
    JSONObject question, answer;
    JSONArray choices;
    List<String> userAnswer;
    int index = 0;
    boolean isSubmitted;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    TextView tvIndex;
    ImageView ivQuestion;
    RadioGroup rgAnswer;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvCurrentTime;
    TextView tvTotalDuration;
    SeekBar sbAudio;
    ImageView ivPlayPause;
    RadioButton rbA, rbB, rbC, rbD;
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
        setContentView(R.layout.activity_skill_test_part1);

        config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        initView();
        fetchTest();

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
                    for (int i = 0; i < questions.length(); i++) {
                        try {
                            if (questions.getJSONObject(i).getJSONObject("answer").getString("text").equals(userAnswer.get(i))) {
                                correctSentences++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ApiService.apiService.submitSkillTestAnswer(correctSentences, skillTestId).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SkillTestPart1Activity.this, "Submitted answer", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart1Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(SkillTestPart1Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(SkillTestPart1Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestPart1Activity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        rgAnswer = (RadioGroup) findViewById(R.id.rgAnswer);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);
        tvTranscript = (TextView) findViewById(R.id.tvTranscript);
        sbAudio = (SeekBar) findViewById(R.id.sbAudio);
        rbA =  (RadioButton) findViewById(R.id.rbA);
        rbB = (RadioButton) findViewById(R.id.rbB);
        rbC = (RadioButton) findViewById(R.id.rbC);
        rbD = (RadioButton) findViewById(R.id.rbD);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mediaPlayer = new MediaPlayer();
        sbAudio.setMax(100);
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
            imageLoader.displayImage(question.getJSONArray("image").getJSONObject(0).getString("url"), ivQuestion);
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
            Toast.makeText(SkillTestPart1Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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