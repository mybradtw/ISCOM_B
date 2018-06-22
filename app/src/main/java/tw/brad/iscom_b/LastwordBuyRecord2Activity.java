package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class LastwordBuyRecord2Activity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private String[] from = {};
    private int[] to = {};
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword_buy_record2);

        listView = findViewById(R.id.lastwordbuy2_list);
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
                R.layout.item_lastwordbuy2, from, to);
        listView.setAdapter(adapter);

    }

    public void back(View view) {
        finish();
    }
    public void buy(View view) {
    }
}
