package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class FeeDetailActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {};
    private int[] to = {};
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);

        listView = findViewById(R.id.fee_detail_list);
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
                R.layout.item_fee_detail, from, to);
        listView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }
}
