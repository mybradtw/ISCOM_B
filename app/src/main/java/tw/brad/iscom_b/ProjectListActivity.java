package tw.brad.iscom_b;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class ProjectListActivity extends AppCompatActivity {
    private SegmentedGroup segmentedGroup;
    private RadioButton workingBtn, closeBtn;

    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {};
    private int[] to = {};
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        segmentedGroup = findViewById(R.id.projectlist_segment);
        segmentedGroup.setTintColor(Color.RED);

        workingBtn = findViewById(R.id.projectlist_working);
        closeBtn = findViewById(R.id.projectlist_close);

        workingBtn.setChecked(true);
        closeBtn.setChecked(false);

        CheckedChangeListener listener = new CheckedChangeListener();
        workingBtn.setOnCheckedChangeListener(listener);
        closeBtn.setOnCheckedChangeListener(listener);

        listView = findViewById(R.id.projectlist_list);
        initListView();
    }


    private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                if ( compoundButton.equals(workingBtn)){
                    //initNotifyListView();
                }else if (compoundButton.equals(closeBtn)){
                    //initNewsListView();
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
                R.layout.item_projectlist, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoEditProject();
            }
        });

    }

    private void gotoEditProject(){
        Intent intent = new Intent(this, EditProjectActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
    public void add(View view) {
        Intent intent = new Intent(this, ProjectAddActivity.class);
        startActivity(intent);
    }


}
