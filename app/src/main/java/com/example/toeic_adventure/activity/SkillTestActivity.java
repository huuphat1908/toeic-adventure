package com.example.toeic_adventure.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.SkillTestAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.SkillTest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillTestActivity extends Fragment {
    ListView lvSkillTest;
    View view;
    ArrayList<SkillTest> arraySkillTest;
    Button btnLevel;
    private String level = "EASY";
    SkillTestAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SkillTestActivity() { }

    public static SkillTestActivity newInstance(String param1, String param2) {
        SkillTestActivity fragment = new SkillTestActivity();
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
        // Init view from fragment_skill_test layout
        view = inflater.inflate(R.layout.fragment_skill_test, container,false);

        // Init adapter and set it to Skill Test List View
        arraySkillTest = new ArrayList<SkillTest>();
        fetchSkillTest();
        lvSkillTest = (ListView) view.findViewById(R.id.listViewSkillTest);
        btnLevel = (Button) view.findViewById(R.id.btnLevel);
        lvSkillTest.setDivider(null);
        adapter = new SkillTestAdapter(
                getContext(),
                R.layout.skill_test_item,
                arraySkillTest
        );
        lvSkillTest.setAdapter(adapter);

        // Add handler for click item event
        lvSkillTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                SkillTest item = (SkillTest) parent.getItemAtPosition(position);
                Intent intent = new Intent(v.getContext(), SkillTestListActivity.class);
                intent.putExtra("title", item.Name);
                startActivity(intent);
            }
        });
        btnLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLevelDialog();
            }
        });
        return view;
    }

    private void showLevelDialog()  {
        Dialog levelDialog = new Dialog(getContext());
        levelDialog.setContentView(R.layout.skill_test_dialog);
        Button btnEasy = (Button) levelDialog.findViewById(R.id.btnEasy);
        Button btnMedium = (Button) levelDialog.findViewById(R.id.btnMedium);
        Button btnHard = (Button) levelDialog.findViewById(R.id.btnHard);
        ImageView ivClose = (ImageView) levelDialog.findViewById(R.id.ivClose);
        Drawable checkIcon = getContext().getResources().getDrawable(R.drawable.check);
        switch (level)  {
            case "EASY":
                btnEasy.setCompoundDrawablesWithIntrinsicBounds(null, null, checkIcon, null);
                break;
            case "MEDIUM":
                btnMedium.setCompoundDrawablesWithIntrinsicBounds(null, null, checkIcon, null);
                break;
            case "HARD":
                btnHard.setCompoundDrawablesWithIntrinsicBounds(null, null, checkIcon, null);
                break;
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelDialog.dismiss();
            }
        });
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "EASY";
                fetchSkillTest();
                levelDialog.dismiss();
            }
        });
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "MEDIUM";
                fetchSkillTest();
                levelDialog.dismiss();
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "HARD";
                fetchSkillTest();
                levelDialog.dismiss();
            }
        });
        levelDialog.setCanceledOnTouchOutside(true);
        levelDialog.show();
    }

    private void fetchSkillTest() {
        ApiService.apiService.getNumberOfSkillTestList(level).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arraySkillTest.clear();
                    arraySkillTest.add(new SkillTest(
                            "Phần 1: Hình ảnh",
                            "Tương ứng với mỗi bức ảnh, bạn sẽ được nghe 04 câu mô tả về nó. Nhiệm vụ của bạn là phải chọn câu mô tả đúng nhất cho bức ảnh",
                            "Bài nghe",
                            resObj.getInt("part1"),
                            R.drawable.part1_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 2: Hỏi đáp",
                            "Bạn sẽ nghe một câu hỏi (hoặc câu nói) và 03 lựa chọn trả lời. Nhiệm vụ của bạn là phải chọn ra câu trả lời đúng nhất trong ba đáp án A-B-C",
                            "Bài nghe",
                            resObj.getInt("part2"),
                            R.drawable.part2_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 3: Hội thoại ngắn",
                            "Bạn sẽ nghe các đoạn hội thoại ngắn. Mỗi đoạn có 03 câu hỏi. Nhiệm vụ của bạn là chọn ra câu trả lời đúng nhất trong 04 đáp án của đề thi",
                            "Bài nghe",
                            resObj.getInt("part3"),
                            R.drawable.part3_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 4: Đoạn thông tin ngắn",
                            "Bạn sẽ nghe các đoạn thông tin ngắn. Mỗi đoạn có 03 câu hỏi. Nhiệm vụ của bạn là chọn ra câu trả lời đúng nhất trong số 04 đáp án được cung câos",
                            "Bài nghe",
                            resObj.getInt("part4"),
                            R.drawable.part4_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 5: Hoàn thành câu",
                            "Bạn sẽ cần phải chọn đáp án đúng nhất trong 04 đáp án A-B-C-D để hoàn thành câu trong đề bài",
                            "Bài đọc",
                            resObj.getInt("part5"),
                            R.drawable.part5_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 6: Hoàn thành đoạn văn",
                            "Mỗi đoạn văn có 03 chỗ trống. Bạn phải điền từ thích hượp còn thiếu vào mỗi chỗ trống trong đoạn văn đó",
                            "Bài đọc",
                            resObj.getInt("part6"),
                            R.drawable.part6_thumbnail
                    ));
                    arraySkillTest.add(new SkillTest(
                            "Phần 7: Hoàn thành đoạn văn",
                            "Đọc đoạn văn và trả lời câu hỏi",
                            "Bài đọc",
                            resObj.getInt("part7"),
                            R.drawable.part7_thumbnail
                    ));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}