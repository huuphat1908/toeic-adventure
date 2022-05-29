package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.model.Answer;
import com.example.toeic_adventure.model.SkillTestList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<com.example.toeic_adventure.model.Question> QuestionList;
    List<com.example.toeic_adventure.model.Answer> AnswerList;

    public QuestionAdapter(Context context,
                           int layout,
                           List<com.example.toeic_adventure.model.Question> questionList,
                           List<com.example.toeic_adventure.model.Answer> answerList,
                           boolean isSubmitted) {
        MyContext = context;
        MyLayout = layout;
        QuestionList = questionList;
        AnswerList = answerList;
    }

    @Override
    public int getCount() {
        return QuestionList.size();
    }

    @Override
    public Object getItem(int i) {
        return QuestionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Init view from layout
        LayoutInflater inflater = (LayoutInflater) MyContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(MyLayout, null);

        // Set test information to layout
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        RadioGroup rgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        RadioButton rbA = (RadioButton) view.findViewById(R.id.rbA);
        RadioButton rbB = (RadioButton) view.findViewById(R.id.rbB);
        RadioButton rbC = (RadioButton) view.findViewById(R.id.rbC);
        RadioButton rbD = (RadioButton) view.findViewById(R.id.rbD);

        int index = i + 1;
        tvQuestion.setText("Câu " + index + ": " + QuestionList.get(i).text);
        try {
            rbA.setText(QuestionList.get(i).choices.getString(0));
            rbB.setText(QuestionList.get(i).choices.getString(1));
            rbC.setText(QuestionList.get(i).choices.getString(2));
            rbD.setText(QuestionList.get(i).choices.getString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (AnswerList.get(i).userAnswer.split(" ")[0]) {
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

        rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerList.get(i).userAnswer = rbA.getText().toString();
            }
        });
        rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerList.get(i).userAnswer = rbB.getText().toString();
            }
        });
        rbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerList.get(i).userAnswer = rbC.getText().toString();
            }
        });
        rbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerList.get(i).userAnswer = rbD.getText().toString();
            }
        });
        return view;
    }
}
