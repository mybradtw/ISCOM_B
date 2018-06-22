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


public class FeeFragment extends Fragment {
    private View mainView;
    private SegmentedGroup segmentedGroup;
    private RadioButton unpaidBtn, paidBtn;
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
                    R.layout.fragment_fee, container, false);
            segmentedGroup = mainView.findViewById(R.id.fee_segment);
            segmentedGroup.setTintColor(Color.RED);

            unpaidBtn = mainView.findViewById(R.id.fee_unpaid);
            paidBtn = mainView.findViewById(R.id.fee_paid);

            unpaidBtn.setChecked(true);
            paidBtn.setChecked(false);

            CheckedChangeListener listener = new CheckedChangeListener();
            unpaidBtn.setOnCheckedChangeListener(listener);
            paidBtn.setOnCheckedChangeListener(listener);

            list = mainView.findViewById(R.id.fee_list);
            initUnpayListView();

        }
        return mainView;
    }
    private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                if ( compoundButton.equals(unpaidBtn)){
                    initUnpayListView();
                }else if (compoundButton.equals(paidBtn)){
                    initPaidListView();
                }
            }
        }
    }

    private void initUnpayListView(){

        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        data.add(d2);

        adapter = new SimpleAdapter(getContext(),data,
                R.layout.item_fee_unpay, from, to);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> adapterView, View view, int i, long l) {
                gotoUnpay();
            }
        });

    }

    private void gotoPaid(){
        Intent intent = new Intent(getContext(), FeeDetailActivity.class);
        startActivity(intent);
    }
    private void gotoUnpay(){
        Intent intent = new Intent(getContext(), FeeDetailActivity.class);
        startActivity(intent);
    }

    private void initPaidListView(){

        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        data.add(d2);

        adapter = new SimpleAdapter(getContext(),data,
                R.layout.item_fee_paid, from, to);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> adapterView, View view, int i, long l) {
                gotoPaid();
            }
        });

    }


}
