package com.example.toeic_adventure;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SkillTestDialog extends Dialog {
    public Context context;

    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;

    public SkillTestDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.skill_test_dialog);

        this.buttonEasy = (Button) findViewById(R.id.button_easy);
        this.buttonMedium  = (Button) findViewById(R.id.button_medium);
        this.buttonHard = (Button) findViewById(R.id.button_hard);

        this.buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLevelClick("Easy");
            }
        });
        this.buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLevelClick("Medium");
            }
        });
        this.buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLevelClick("Hard");
            }
        });
    }

    private void buttonLevelClick(String level)  {

        switch (level) {
            case "Easy":
            case "Medium":
            case "Hard": {
                Toast.makeText(this.context, level, Toast.LENGTH_LONG).show();
                break;
            }
            default:
                Toast.makeText(this.context, "Chicken", Toast.LENGTH_LONG).show();
                break;
        }
        this.dismiss(); // Close Dialog

    }

}