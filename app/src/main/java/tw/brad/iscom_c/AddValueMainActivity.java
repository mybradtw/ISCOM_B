package tw.brad.iscom_c;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class AddValueMainActivity extends AppCompatActivity {
    private SegmentedGroup segmentedGroup;
    private RadioButton addBtn, listBtn;
    private View addView;
    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {};
    private int[] to = {};
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_value_main);

        addView = findViewById(R.id.addvaluemain_additems);
        listView = findViewById(R.id.addvaluemain_trade_list);
        initListView();

        segmentedGroup = findViewById(R.id.addvaluemain_segment);
        segmentedGroup.setTintColor(Color.RED);

        addBtn = findViewById(R.id.addvaluemain_add);
        listBtn = findViewById(R.id.addvaluemain_trade);

        addBtn.setChecked(true);
        listBtn.setChecked(false);

        CheckedChangeListener listener = new CheckedChangeListener();
        addBtn.setOnCheckedChangeListener(listener);
        listBtn.setOnCheckedChangeListener(listener);
    }

    private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                if ( compoundButton.equals(addBtn)){
                    addView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }else if (compoundButton.equals(listBtn)){
                    addView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void initListView(){
        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        data.add(d2);

        adapter = new SimpleAdapter(this,data,
                R.layout.item_addvaluemain_record, from, to);
        listView.setAdapter(adapter);

    }
}
