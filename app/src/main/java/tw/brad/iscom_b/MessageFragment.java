package tw.brad.iscom_b;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

import info.hoang8f.android.segmented.SegmentedGroup;


public class MessageFragment extends Fragment {
    private View mainView;
    private SegmentedGroup segmentedGroup;
    private RadioButton notifyBtn, newsBtn;
    private ListView list;
    private SimpleAdapter adapter;
    private String[] from = {};
    private int[] to = {};
    private LinkedList<HashMap<String,String>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_message, container, false);
            segmentedGroup = mainView.findViewById(R.id.message_segment);
            segmentedGroup.setTintColor(Color.RED);

            notifyBtn = mainView.findViewById(R.id.message_notify);
            newsBtn = mainView.findViewById(R.id.message_news);

            notifyBtn.setChecked(true);
            newsBtn.setChecked(false);

            CheckedChangeListener listener = new CheckedChangeListener();
            notifyBtn.setOnCheckedChangeListener(listener);
            newsBtn.setOnCheckedChangeListener(listener);

            list = mainView.findViewById(R.id.message_list);
            initNotifyListView();
        }
        return mainView;
    }

    private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                if ( compoundButton.equals(notifyBtn)){
                    initNotifyListView();
                }else if (compoundButton.equals(newsBtn)){
                    initNewsListView();
                }
            }
        }
    }


    private void initNotifyListView(){

        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        data.add(d2);

        adapter = new SimpleAdapter(getContext(),data,
                R.layout.item_message_notify, from, to);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoDetailSchedule();
            }
        });

    }

    private void initNewsListView(){

        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        data.add(d2);

        adapter = new SimpleAdapter(getContext(),data,
                R.layout.item_message_news, from, to);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoDetailNews();
            }
        });

    }


    private void gotoDetailSchedule(){
        Intent intent = new Intent(getContext(), DetailScheduleActivity.class);
        startActivity(intent);
    }

    private void gotoDetailNews(){
        Intent intent = new Intent(getContext(), DetailNewsActivity.class);
        startActivity(intent);
    }

}
