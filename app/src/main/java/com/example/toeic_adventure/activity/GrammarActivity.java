package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.FullTestCollectionAdapter;
import com.example.toeic_adventure.adapter.GrammarListAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.FullTestCollection;
import com.example.toeic_adventure.model.GrammarList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrammarActivity extends Fragment {
    View rootView;
    ListView lvGrammar;
    ArrayList<GrammarList> arrayGrammarList;
    GrammarListAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GrammarActivity() { }

    public static GrammarActivity newInstance(String param1, String param2) {
        GrammarActivity fragment = new GrammarActivity();
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
        rootView = inflater.inflate(R.layout.fragment_grammar, container, false);
        initView();
        arrayGrammarList = new ArrayList<GrammarList>();
        fetchGrammarList();
        adapter = new GrammarListAdapter(
                getContext(),
                R.layout.grammar_list_item,
                arrayGrammarList
        );
        lvGrammar.setAdapter(adapter);

        lvGrammar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GrammarList item = (GrammarList) parent.getItemAtPosition(position);
                Intent grammarDetailIntent = new Intent(rootView.getContext(), GrammarDetailActivity.class);
                grammarDetailIntent.putExtra("id", item.id);
                grammarDetailIntent.putExtra("name", item.name);
                startActivity(grammarDetailIntent);
            }
        });
        return rootView;
    }

    private void initView() {
        lvGrammar = (ListView) rootView.findViewById(R.id.lvGrammar);
    }

    private void fetchGrammarList() {
        ApiService.apiService.getGrammarList().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    Log.d("AAA", resObj.toString());
                    arrayGrammarList.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject grammarList = results.getJSONObject(i);
                        String name = grammarList.getString("name");
                        String id = grammarList.getString("id");
                        arrayGrammarList.add(new GrammarList(name, id));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "JSON object error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}