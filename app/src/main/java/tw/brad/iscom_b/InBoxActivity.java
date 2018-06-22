package tw.brad.iscom_b;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class InBoxActivity extends AppCompatActivity {
    private View finish;
    private SegmentedGroup segmentedGroup;
    private RadioButton newBtn, responsedBtn;
    private ListView list;
    private SimpleAdapter adapter;
    private LinkedList<HashMap<String,String>> data;
    private String[] from = {};
    private int[] to = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_box);

        finish = findViewById(R.id.inbox_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        segmentedGroup = findViewById(R.id.inbox_segment);
        segmentedGroup.setTintColor(Color.RED);

        newBtn = findViewById(R.id.inbox_new_segment);
        responsedBtn = findViewById(R.id.inbox_responsed_segment);

        newBtn.setChecked(true);
        responsedBtn.setChecked(false);

        list = findViewById(R.id.inbox_list);
        initListView();

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
                R.layout.item_inbox_new, from, to);
        list.setAdapter(adapter);

    }

}
