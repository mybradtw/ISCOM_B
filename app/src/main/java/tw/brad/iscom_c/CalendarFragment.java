package tw.brad.iscom_c;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;


public class CalendarFragment extends Fragment {
    private View mainView, nextView, prevView, businessView, inboxView, searchView;
    private TextView monthView;
    private ImageView addView;
    private CalendarView calendarView;
    private ListView eventList;
    private int intYear, intMonth;
    private Dialog dialogBeforeAddEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intYear = Calendar.getInstance().get(Calendar.YEAR);
        intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_calendar, container, false);
            calendarView = mainView.findViewById(R.id.calendar_cal);
            prevView = mainView.findViewById(R.id.calendar_prev);
            nextView = mainView.findViewById(R.id.calendar_next);
            monthView = mainView.findViewById(R.id.calendar_month);
            addView = mainView.findViewById(R.id.calendar_add);
            businessView = mainView.findViewById(R.id.calendar_search_business);
            inboxView = mainView.findViewById(R.id.calendar_inbox);
            searchView = mainView.findViewById(R.id.calendar_search_calendar);

            prevView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (intMonth == 1){
                        intMonth = 12;
                        intYear--;
                    }else{
                        intMonth--;
                    }
                    changeMonth();
                }
            });
            nextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (intMonth == 12){
                        intMonth = 1;
                        intYear++;
                    }else{
                        intMonth++;
                    }
                    changeMonth();
                }
            });

            changeMonth();

            eventList = mainView.findViewById(R.id.calendar_eventlist);
            updateEventList(true);


            addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addEvent();
                }
            });

            businessView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoBusinessList();
                }
            });

            inboxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoInBox();
                }
            });

            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoSearch();
                }
            });

        }
        return mainView;
    }

    private void updateEventList(boolean init){
        int[] to = {R.id.item_calendar_event_begintime, R.id.item_calendar_event_endtime,
                R.id.item_calendar_event_kind, R.id.item_calendar_event_place};
        String[] from = {"begintime","endtime","kind","place"};
        LinkedList<HashMap<String,String>> data = new LinkedList<>();
        HashMap<String,String> d0 = new HashMap<>();
        d0.put(from[0], "09:00");
        d0.put(from[1], "10:00");
        d0.put(from[2], "專案種類");
        d0.put(from[3], "地點");
        data.add(d0);

        HashMap<String,String> d1 = new HashMap<>();
        d1.put(from[0], "09:00");
        d1.put(from[1], "10:00");
        d1.put(from[2], "專案種類");
        d1.put(from[3], "地點");
        data.add(d1);

        HashMap<String,String> d2 = new HashMap<>();
        d2.put(from[0], "09:00");
        d2.put(from[1], "10:00");
        d2.put(from[2], "專案種類");
        d2.put(from[3], "地點");
        data.add(d2);

        HashMap<String,String> d3 = new HashMap<>();
        d3.put(from[0], "09:00");
        d3.put(from[1], "10:00");
        d3.put(from[2], "專案種類");
        d3.put(from[3], "地點");
        data.add(d3);

        HashMap<String,String> d4 = new HashMap<>();
        d4.put(from[0], "09:00");
        d4.put(from[1], "10:00");
        d4.put(from[2], "專案種類");
        d4.put(from[3], "地點");
        data.add(d4);

        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                data, R.layout.item_calendar_event, from,to);

        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoDetailSchedule();
            }
        });
    }

    private void gotoBusinessList(){
        Intent intent = new Intent(getContext(), BusinessListActivity.class);
        startActivity(intent);
    }

    // 變更月份
    private void changeMonth(){
        calendarView.setYearMonth(intYear, intMonth);
        monthView.setText(intMonth + "月");
    }

    private void addEvent(){
        dialogBeforeAddEvent = new Dialog(getContext());
        dialogBeforeAddEvent.setContentView(R.layout.dialog_before_addevent);
        View add = dialogBeforeAddEvent.findViewById(R.id.before_addevent);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBeforeAddEvent.dismiss();
                gotoAddSchedule();
            }
        });

        // 使對話筐變大
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBeforeAddEvent.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogBeforeAddEvent.getWindow().setAttributes(lp);
        dialogBeforeAddEvent.show();

    }

    private void gotoAddSchedule(){
        Intent intent = new Intent(getContext(), AddScheduleActivity.class);
        startActivity(intent);
    }

    private void gotoDetailSchedule(){
        Intent intent = new Intent(getContext(), DetailScheduleActivity.class);
        startActivity(intent);
    }

    private void gotoInBox(){
        Intent intent = new Intent(getContext(), InBoxActivity.class);
        startActivity(intent);
    }

    private void gotoSearch(){
        Intent intent = new Intent(getContext(), SearchCalendarActivity.class);
        startActivity(intent);
    }

}
