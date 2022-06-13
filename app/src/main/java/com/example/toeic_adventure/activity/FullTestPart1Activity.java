package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.QuestionPart1Adapter;
import com.example.toeic_adventure.model.Answer;
import com.example.toeic_adventure.model.Question;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FullTestPart1Activity extends AppCompatActivity {
    JSONArray questions;
    JSONObject question, answer;
    int index = 0;
    boolean isSubmitted;
    List<Boolean> isSubmittedList;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    TextView tvIndex;
    ImageView ivClose;
    ImageView ivQuestion;
    ListView lvQuestion;
    ImageView ivNext;
    ImageView ivPrev;
    TextView tvCurrentTime;
    TextView tvTotalDuration;
    SeekBar sbAudio;
    ImageView ivPlayPause;
    TextView tvTranscript;
    Button btnSubmit;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    ArrayList<Question> questionList;
    ArrayList<Answer> answerList;
    QuestionPart1Adapter adapter;

    int correctSentences = 0;
    int completedSentences = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test_part1);

        config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        initView();
        fetchTest();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
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
        intent.putExtra("part", 1);
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        tvIndex = (TextView) findViewById(R.id.tvIndex);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        lvQuestion = (ListView) findViewById(R.id.lvQuestion);
        questionList = new ArrayList<Question>();
        answerList = new ArrayList<Answer>();
        isSubmittedList = new ArrayList<Boolean>();
        adapter = new QuestionPart1Adapter(
                FullTestPart1Activity.this,
                R.layout.question_layout_item,
                questionList,
                answerList,
                isSubmittedList
        );
        lvQuestion.setAdapter(adapter);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);
        sbAudio = (SeekBar) findViewById(R.id.sbAudio);
        mediaPlayer = new MediaPlayer();
        sbAudio.setMax(100);
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
            imageLoader.displayImage(question.getJSONArray("image").getJSONObject(0).getString("url"), ivQuestion);

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

    private void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(question.getJSONArray("sound").getJSONObject(0).getString("url"));
            mediaPlayer.prepare();
            tvTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        } catch (Exception e) {
            Toast.makeText(FullTestPart1Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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