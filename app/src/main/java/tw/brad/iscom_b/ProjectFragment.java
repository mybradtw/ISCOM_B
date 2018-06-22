package tw.brad.iscom_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class ProjectFragment extends Fragment {
    private View mainView;
    private ListView list;
    private SimpleAdapter adapter;
    private LinkedList<HashMap<String,String>> data;
    private String[] from = {"name"};
    private int[] to = {R.id.item_calendar_business_name};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_project, container, false);

            list = mainView.findViewById(R.id.project_list);
            initListView();

        }
        return mainView;
    }

    private void initListView(){
        data = new LinkedList<>();

        HashMap<String, String> d0 = new HashMap<>();
        d0.put(from[0], "張律師事務所");
        data.add(d0);

        HashMap<String, String> d1 = new HashMap<>();
        d1.put(from[0], "測試牙醫師診所");
        data.add(d1);

        HashMap<String, String> d2 = new HashMap<>();
        d2.put(from[0], "台中醫院藥劑師");
        data.add(d2);

        adapter = new SimpleAdapter(getContext(),data,
                R.layout.item_calendar_business, from, to);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoProjectList();
            }
        });
    }
    private void gotoProjectList(){
        Intent intent = new Intent(getContext(), ProjectListActivity.class);
        startActivity(intent);
    }


}
