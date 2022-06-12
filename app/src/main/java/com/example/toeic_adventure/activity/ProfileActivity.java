package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
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
                    JSONObject avatar = resObj.getJSONObject("avatar");
                    JSONObject done = resObj.getJSONObject("done");
                    txtEmailUser.setText(resObj.getString("email"));
                    Glide.with(ProfileActivity.this).load(Url.baseUrl + avatar.getString("url")).into(imProfile);
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
                    Toast.makeText(getContext(),"JSON object error", Toast.LENGTH_SHORT).show();
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