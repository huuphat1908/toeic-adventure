package com.example.toeic_adventure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillTestActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillTestActivity extends Fragment {
    ListView lvSkillTest;
    View view;
    ArrayList<SkillTest> arraySkillTest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SkillTestActivity() {
        // Required empty public constructor
    }

    private ArrayList<SkillTest> initArraySkillTest() {
        ArrayList<SkillTest> array = new ArrayList<SkillTest>();
        array.add(new SkillTest(
                "Phần 1: Hình ảnh",
                "Tương ứng với mỗi bức ảnh, bạn sẽ được nghe 04 câu mô tả về nó. Nhiệm vụ của bạn là phải chọn câu mô tả đúng nhất cho bức ảnh",
                TestType.LISTENING,
                75,
                R.drawable.part1_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 2: Hỏi đáp",
                "Bạn sẽ nghe một câu hỏi (hoặc câu nói) và 03 lựa chọn trả lời. Nhiệm vụ của bạn là phải chọn ra câu trả lời đúng nhất trong ba đáp án A-B-C",
                TestType.LISTENING,
                43,
                R.drawable.part2_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 3: Hội thoại ngắn",
                "Bạn sẽ nghe các đoạn hội thoại ngắn. Mỗi đoạn có 03 câu hỏi. Nhiệm vụ của bạn là chọn ra câu trả lời đúng nhất trong 04 đáp án của đề thi",
                TestType.LISTENING,
                53,
                R.drawable.part3_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 4: Đoạn thông tin ngắn",
                "Bạn sẽ nghe các đoạn thông tin ngắn. Mỗi đoạn có 03 câu hỏi. Nhiệm vụ của bạn là chọn ra câu trả lời đúng nhất trong số 04 đáp án được cung câos",
                TestType.LISTENING,
                36,
                R.drawable.part4_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 5: Hoàn thành câu",
                "Bạn sẽ cần phải chọn đáp án đúng nhất trong 04 đáp án A-B-C-D để hoàn thành câu trong đề bài",
                TestType.READING,
                123,
                R.drawable.part5_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 6: Hoàn thành đoạn văn",
                "Mỗi đoạn văn có 03 chỗ trống. Bạn phải điền từ thích hượp còn thiếu vào mỗi chỗ trống trong đoạn văn đó",
                TestType.READING,
                16,
                R.drawable.part6_thumbnail
        ));
        array.add(new SkillTest(
                "Phần 7: Hoàn thành đoạn văn",
                "Đọc đoạn văn và trả lời câu hỏi",
                TestType.READING,
                53,
                R.drawable.part7_thumbnail
        ));

        return array;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkillTest.
     */
    // TODO: Rename and change types and number of parameters
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
        arraySkillTest = initArraySkillTest();
        lvSkillTest = (ListView) view.findViewById(R.id.listViewSkillTest);
        lvSkillTest.setDivider(null);
        SkillTestAdapter adapter = new SkillTestAdapter(
                getContext(),
                R.layout.skill_test_item,
                arraySkillTest
        );
        lvSkillTest.setAdapter(adapter);

        // Add handler for click item event
        lvSkillTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Toast.makeText(getContext(), "You have click item " + position, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}