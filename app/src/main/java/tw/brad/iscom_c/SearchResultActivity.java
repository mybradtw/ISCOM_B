package tw.brad.iscom_c;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        expandableListView = findViewById(R.id.searchresult_list);
        initView();
    }

    private void initView(){
        expandableListView.setAdapter(new MyExpandableListAdapter());
        expandableListView.setGroupIndicator(null); // 不會出現指示符號
        expandableListView.setOnGroupCollapseListener(
                new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                expandableListView.expandGroup(i);
            }
        });
        expandableListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(
                    ExpandableListView expandableListView, View view,
                    int i, int i1, long l) {
                Intent intent = new Intent(SearchResultActivity.this, SearchDetailActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private class MyExpandableListAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int i) {
            return i==0?2:3;
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(
                int i, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(SearchResultActivity.this).inflate(
                        R.layout.item_expand_group, viewGroup, false);
                TextView title = (TextView) view.findViewById(
                        R.id.expand_group_title);
                expandableListView.expandGroup(i);
            }
            return view;
        }

        @Override
        public View getChildView(
                int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(SearchResultActivity.this).inflate(
                        R.layout.item_calendar_event, viewGroup, false);
            }
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    public void backCalendar(View view) {
        finish();
    }

    public void backLast(View view) {
        finish();
    }
}
