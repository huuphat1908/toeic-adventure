package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillTestPart1Activity extends AppCompatActivity {
    JSONArray questions;
    JSONObject question, answer;
    int index = 1;
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
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    private void fetchTest() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        ApiService.apiService.getSkillTest(id).enqueue(new Callback<Object>() {
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
        sbAudio = (SeekBar) findViewById(R.id.sbAudio);
        mediaPlayer = new MediaPlayer();
        sbAudio.setMax(100);
    }

    private void handleQuestion() {
        try {
            question = questions.getJSONObject(index - 1).getJSONObject("question");
            answer = questions.getJSONObject(index - 1).getJSONObject("answer");
            tvIndex.setText(index + "/" + questions.length());
            imageLoader.displayImage(question.getJSONArray("image").getJSONObject(0).getString("url"), ivQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNavigateIcon() {
        if (index == 1) {
            ivPrev.setImageDrawable(null);
        } else {
            ivPrev.setImageResource(R.drawable.prev_question);
        }
        if (index == questions.length()) {
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