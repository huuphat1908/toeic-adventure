package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.toeic_adventure.R;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<com.example.toeic_adventure.model.Question> QuestionList;
    List<com.example.toeic_adventure.model.Answer> AnswerList;
    List<Boolean> IsSubmittedList;

    public QuestionAdapter(Context context,
                           int layout,
                           List<com.example.toeic_adventure.model.Question> questionList,
                           List<com.example.toeic_adventure.model.Answer> answerList,
                           List<Boolean> isSubmittedList) {
        MyContext = context;
        MyLayout = layout;
        QuestionList = questionList;
        AnswerList = answerList;
        IsSubmittedList = isSubmittedList;
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

        if (QuestionList.get(i).text.equals("")) {
            tvQuestion.setVisibility(View.INVISIBLE);
        } else {
            tvQuestion.setText(QuestionList.get(i).text);
        }
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
        rbB.setOnClickListener(new View.OnClickListener() {
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
        if (IsSubmittedList.get(i)) {
            rgAnswer.setClickable(false);
            rbA.setClickable(false);
            rbB.setClickable(false);
            rbC.setClickable(false);
            rbD.setClickable(false);
            rbA.setTextColor(MyContext.getResources().getColor(R.color.black));
            rbB.setTextColor(MyContext.getResources().getColor(R.color.black));
            rbC.setTextColor(MyContext.getResources().getColor(R.color.black));
            rbD.setTextColor(MyContext.getResources().getColor(R.color.black));
            switch (rgAnswer.getCheckedRadioButtonId()) {
                case -1:
                    switch (AnswerList.get(i).text.split(" ")[0]) {
                        case "(A)":
                            rbA.setTextColor(MyContext.getResources().getColor(R.color.pink));
                            break;
                        case "(B)":
                            rbB.setTextColor(MyContext.getResources().getColor(R.color.pink));
                            break;
                        case "(C)":
                            rbC.setTextColor(MyContext.getResources().getColor(R.color.pink));
                            break;
                        case "(D)":
                            rbD.setTextColor(MyContext.getResources().getColor(R.color.pink));
                            break;
                    }
                    break;
                case R.id.rbA:
                    if (!AnswerList.get(i).text.split(" ")[0].equals("(A)")) {
                        rbA.setTextColor(MyContext.getResources().getColor(R.color.pink));
                    } else {
                        rbA.setTextColor(MyContext.getResources().getColor(R.color.green));
                    }
                    break;
                case R.id.rbB:
                    if (!AnswerList.get(i).text.split(" ")[0].equals("(B)")) {
                        rbB.setTextColor(MyContext.getResources().getColor(R.color.pink));
                    } else {
                        rbB.setTextColor(MyContext.getResources().getColor(R.color.green));
                    }
                    break;
                case R.id.rbC:
                    if (!AnswerList.get(i).text.split(" ")[0].equals("(C)")) {
                        rbC.setTextColor(MyContext.getResources().getColor(R.color.pink));
                    } else {
                        rbC.setTextColor(MyContext.getResources().getColor(R.color.green));
                    }
                    break;
                case R.id.rbD:
                    if (!AnswerList.get(i).text.split(" ")[0].equals("(D)")) {
                        rbD.setTextColor(MyContext.getResources().getColor(R.color.pink));
                    } else {
                        rbD.setTextColor(MyContext.getResources().getColor(R.color.green));
                    }
                    break;
            }
        }
        return view;
    }
}

