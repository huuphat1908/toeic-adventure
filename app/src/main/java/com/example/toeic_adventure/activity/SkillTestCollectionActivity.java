package com.example.toeic_adventure.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.toeic_adventure.adapter.SkillTestCollectionAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.SkillTestCollection;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillTestCollectionActivity extends Fragment {
    ListView lvSkillTestCollection;
    View view;
    ArrayList<SkillTestCollection> arraySkillTestCollection;
    Button btnLevel;
    private String level = "EASY";
    SkillTestCollectionAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SkillTestCollectionActivity() { }

    public static SkillTestCollectionActivity newInstance(String param1, String param2) {
        SkillTestCollectionActivity fragment = new SkillTestCollectionActivity();
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
        arraySkillTestCollection = new ArrayList<SkillTestCollection>();
        fetchSkillTest();
        lvSkillTestCollection = (ListView) view.findViewById(R.id.listViewSkillTest);
        btnLevel = (Button) view.findViewById(R.id.btnLevel);
        lvSkillTestCollection.setDivider(null);
        adapter = new SkillTestCollectionAdapter(
                getContext(),
                R.layout.skill_test_collection_item,
                arraySkillTestCollection
        );
        lvSkillTestCollection.setAdapter(adapter);

        // Add handler for click item event
        lvSkillTestCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                SkillTestCollection item = (SkillTestCollection) parent.getItemAtPosition(position);
                Intent skillTestListIntent = new Intent(v.getContext(), SkillTestListActivity.class);
                skillTestListIntent.putExtra("title", item.Name);
                skillTestListIntent.putExtra("part", item.Part);
                skillTestListIntent.putExtra("level", level);
                startActivity(skillTestListIntent);
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
        ApiService.apiService.getSkillTestCollection(level).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arraySkillTestCollection.clear();
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 1: H??nh ???nh",
                            "T????ng ???ng v???i m???i b???c ???nh, b???n s??? ???????c nghe 04 c??u m?? t??? v??? n??. Nhi???m v??? c???a b???n l?? ph???i ch???n c??u m?? t??? ????ng nh???t cho b???c ???nh",
                            "B??i nghe",
                            resObj.getInt("part1"),
                            R.drawable.part1_thumbnail,
                            "1"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 2: H???i ????p",
                            "B???n s??? nghe m???t c??u h???i (ho???c c??u n??i) v?? 03 l???a ch???n tr??? l???i. Nhi???m v??? c???a b???n l?? ph???i ch???n ra c??u tr??? l???i ????ng nh???t trong ba ????p ??n A-B-C",
                            "B??i nghe",
                            resObj.getInt("part2"),
                            R.drawable.part2_thumbnail,
                            "2"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 3: H???i tho???i ng???n",
                            "B???n s??? nghe c??c ??o???n h???i tho???i ng???n. M???i ??o???n c?? 03 c??u h???i. Nhi???m v??? c???a b???n l?? ch???n ra c??u tr??? l???i ????ng nh???t trong 04 ????p ??n c???a ????? thi",
                            "B??i nghe",
                            resObj.getInt("part3"),
                            R.drawable.part3_thumbnail,
                            "3"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 4: ??o???n th??ng tin ng???n",
                            "B???n s??? nghe c??c ??o???n th??ng tin ng???n. M???i ??o???n c?? 03 c??u h???i. Nhi???m v??? c???a b???n l?? ch???n ra c??u tr??? l???i ????ng nh???t trong s??? 04 ????p ??n ???????c cung c???p",
                            "B??i nghe",
                            resObj.getInt("part4"),
                            R.drawable.part4_thumbnail,
                            "4"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 5: Ho??n th??nh c??u",
                            "B???n s??? c???n ph???i ch???n ????p ??n ????ng nh???t trong 04 ????p ??n A-B-C-D ????? ho??n th??nh c??u trong ????? b??i",
                            "B??i ?????c",
                            resObj.getInt("part5"),
                            R.drawable.part5_thumbnail,
                            "5"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 6: Ho??n th??nh ??o???n v??n",
                            "M???i ??o???n v??n c?? 03 ch??? tr???ng. B???n ph???i ??i???n t??? th??ch h?????p c??n thi???u v??o m???i ch??? tr???ng trong ??o???n v??n ????",
                            "B??i ?????c",
                            resObj.getInt("part6"),
                            R.drawable.part6_thumbnail,
                            "6"
                    ));
                    arraySkillTestCollection.add(new SkillTestCollection(
                            "Ph???n 7: Ho??n th??nh ??o???n v??n",
                            "?????c ??o???n v??n v?? tr??? l???i c??u h???i",
                            "B??i ?????c",
                            resObj.getInt("part7"),
                            R.drawable.part7_thumbnail,
                            "7"
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