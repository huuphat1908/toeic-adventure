package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.api.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View rootView;
    private ImageView imProfile;
    private TextView txtEmailUser;
    private TextView txtJoinedDay;
    private TextView txtScoreFullTest;
    private TextView txtScoreSkillTest;
    private TextView txtPredictedScore;
    private Button btnLogout;
    private LinearLayout linearLayoutBottom;

    public ProfileActivity() {
    }

    public static ProfileActivity newInstance(String param1, String param2) {
        ProfileActivity fragment = new ProfileActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        imProfile = (ImageView) rootView.findViewById(R.id.imProfile);
        txtEmailUser = (TextView) rootView.findViewById(R.id.txtEmailUser);
        txtJoinedDay = (TextView) rootView.findViewById(R.id.txtJoinedDay);
        txtScoreFullTest = (TextView) rootView.findViewById(R.id.txtScoreFullTest);
        txtScoreSkillTest = (TextView) rootView.findViewById(R.id.txtScoreSkillTest);
        txtPredictedScore = (TextView) rootView.findViewById(R.id.txtPredictedScore);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        linearLayoutBottom = (LinearLayout) rootView.findViewById(R.id.linearLayoutBottom);

        fetchProfileUser();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        params.setMargins(0, 0, 0, getHeightNavigationBar());
        linearLayoutBottom.setLayoutParams(params);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = MainActivity.localStorage.edit();
                editor.clear();
                editor.commit();
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long getDaysDiff(String startDate) {
        Date today = new Date();
        java.util.Date endDate = Date.from( Instant.parse(startDate));

        long getDay = today.getTime() - endDate.getTime();

        long getDaysDiff = getDay / (24 * 60 * 60 * 1000);

        return getDaysDiff;
    }

    private void fetchProfileUser() {
        ApiService.apiService.getProfileUser().enqueue(new Callback<Object>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    if (resObj.has("avatar")) {
                        JSONObject avatar = resObj.getJSONObject("avatar");
                        Glide.with(ProfileActivity.this).load(Url.baseUrl + avatar.getString("url")).into(imProfile);
                    } else {
                        Glide.with(ProfileActivity.this)
                                .load("data:image/png;base64,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").into(imProfile);
                    }
                    JSONObject done = resObj.getJSONObject("done");
                    txtEmailUser.setText(resObj.getString("email"));
                    txtJoinedDay.setText((int) getDaysDiff(resObj.getString("joinDate")) + " day ago");
                    txtScoreFullTest.setText(String.valueOf(done.getInt("fullTest")));
                    txtScoreSkillTest.setText(String.valueOf(done.getInt("skillTest")));
                    if (resObj.getInt("predictedScore") == -1) {
                        txtPredictedScore.setText("Complete at least one full test to get predicted score");
                    } else {
                        txtPredictedScore.setText(String.valueOf(resObj.getInt("predictedScore")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Bad request", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    };

    private int getHeightNavigationBar() {
        Resources resources = rootView.getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}