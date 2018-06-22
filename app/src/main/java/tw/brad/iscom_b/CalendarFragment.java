package tw.brad.iscom_b;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.Request;
import okhttp3.Response;


public class CalendarFragment extends Fragment {
    private View mainView, nextView, prevView, businessView, searchView;
    private TextView monthView, inboxView;
    private ImageView addView;
    private CalendarView calendarView;
    private ListView eventList;
    private int intYear, intMonth, intDay;
    private Dialog dialogBeforeAddEvent;
    private String startDate, endDate;
    private MainApp mainApp;
    private HashMap<String,LinkedList<Schedule>> schedules; // date => Schedule
    private UIHandler uiHandler;
    private int[] to = {R.id.item_calendar_event_begintime, R.id.item_calendar_event_endtime,
            R.id.item_calendar_event_kind, R.id.item_calendar_event_place};
    private String[] from = {"begintime","endtime","kind","place"};
    private LinkedList<HashMap<String,Object>> data = new LinkedList<>();
    private SimpleAdapter adapter;

    private String inboxBoxNum = "0";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainApp = (MainApp) ((Activity)context).getApplication();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intYear = Calendar.getInstance().get(Calendar.YEAR);
        intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        intDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        schedules = new HashMap<>();
        uiHandler = new UIHandler();
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
            initEventList();

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

        getInboxNum();



        return mainView;
    }

    private void initEventList(){
        adapter = new SimpleAdapter(getContext(),
                data, R.layout.item_calendar_event, from,to);

        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoDetailSchedule(i);
            }
        });

        todayEventList();
    }

    public void todayEventList(){
        String key = intYear+"-"+ BradUtils.preZero(intMonth)+"-"+ BradUtils.preZero(intDay);
        updateEventList(key);
    }

    public void updateEventList(String key){
        data.clear();

        if (schedules.containsKey(key)){
            LinkedList<Schedule> events = schedules.get(key);
            for (Schedule schedule : events){
                HashMap<String,Object> d1 = new HashMap<>();
                d1.put(from[0], schedule.c_date_start.substring(11).substring(0,5));
                d1.put(from[1], schedule.c_date_end.substring(11).substring(0,5));
                d1.put(from[2], schedule.meeting_type);
                d1.put(from[3], schedule.meeting_place);
                d1.put("schedule", schedule);
                data.add(d1);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void gotoBusinessList(){
        Intent intent = new Intent(getContext(), BusinessListActivity.class);
        startActivity(intent);
    }

    // 變更月份
    private void changeMonth(){
        // 透過 intYear, intMonth => startDate, endDate
        getStartEndDate(intYear, intMonth);

        // 撈取遠端資料
        getSchedules();

        calendarView.setYearMonth(intYear, intMonth);
        monthView.setText(intMonth + "月");
    }

    private void getStartEndDate(int year, int month){
        int firstWeek = MyCalendar.count_first_day(year, month);
        int days = MyCalendar.count_days(year, month);

        int lastMonthCount = MyCalendar.count_days(
                (month!=12?year:year-1),(month!=12)?month-1:1);

        // 起始日期: 先計算上個月的起日, 如果沒有, 就是這個月的1日
        if (firstWeek == 0){
            //這個月的1日為起始日期;
            startDate = year + "-" + month + "-1";
        }else{
            // lastMonthCount - firstWeek + 1
            if (month == 1){
                startDate = (year-1) + "-12-" + (lastMonthCount - firstWeek + 1);
            }else{
                startDate = year + "-" + (month-1) + "-" + (lastMonthCount - firstWeek + 1);
            }
        }

        // 結束日期: 計算 days + firstWeek
        if ((days + firstWeek) % 7 == 0){
            endDate = year + "-" + month + "-" + days;
        }else{
            if (month == 12){
                endDate = (year+1) + "-1-" + (7 - ((days + firstWeek) % 7));
            }else{
                endDate = year + "-" + (month+1) + "-" + (7 - ((days + firstWeek) % 7));
            }
        }
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

    private void gotoDetailSchedule(int i){
        Intent intent = new Intent(getContext(), DetailScheduleActivity.class);
        Schedule schedule = (Schedule)(data.get(i).get("schedule"));
        intent.putExtra("month", intMonth);
        intent.putExtra("schedule", schedule);
        intent.putExtra("isEditable", true);
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


    private void getSchedules(){
        new Thread(){
            @Override
            public void run() {

                String url = BradUtils.apiUrl + "GetCalendar/"
                        + startDate + "/" + endDate;
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Bearer " + mainApp.token)
                        .build();
                try {
                    Response response =  mainApp.client.newCall(request).execute();
                    String json = response.body().string();
                    parseSchedule(json);
                } catch (IOException e) {
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }

    private void parseSchedule(String json){
        try {
            JSONArray root = new JSONArray(json);
            for (int i=0; i<root.length(); i++){
                JSONObject row = root.getJSONObject(i);

                // 建立 Schedule 物件實體
                Schedule schedule = new Schedule();
                schedule.ce_id = row.getString("CE_ID");
                schedule.m_id = row.getString("M_ID");
                schedule.owner_id = row.getString("OWNER_ID");
                schedule.guest = row.getString("GUEST");
                schedule.p_id = row.getString("P_ID");
                schedule.p_name = row.getString("P_NAME");
                schedule.p_class = row.getString("P_CLASS");
                schedule.c_date_start = row.getString("C_DATE_START");
                schedule.c_date_end = row.getString("C_DATE_END");
                schedule.meeting_type = row.getString("MEETING_TYPE");
                schedule.meeting_title = row.getString("MEETING_TITLE");
                schedule.meeting_place = row.getString("MEETING_PLACE");
                schedule.meeting_info = row.getString("MEETING_INFO");
                schedule.status = row.getString("STATUS");

                String dateKey = schedule.c_date_start.substring(0,10);
                if (schedules.containsKey(dateKey)){
                    // 已存在相同日期的資料 LinkedList<Schedule>
                    schedules.get(dateKey).add(schedule);
                }else{
                    // 尚未有相同日期的資料 LinkedList<Schedule>
                    LinkedList<Schedule> sData = new LinkedList<>();
                    sData.add(schedule);
                    schedules.put(dateKey, sData);
                }
            }

            uiHandler.sendEmptyMessage(0);

        }catch (Exception e){
            Log.v("brad", "Exception: " + e.toString());
        }
    }

    private void getInboxNum(){
        new Thread(){
            @Override
            public void run() {
                String url = BradUtils.apiUrl + "GetInboxNum/false";
                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", "Bearer " + mainApp.token)
                        .build();
                try {
                    Response response =  mainApp.client.newCall(request).execute();
                    String json = response.body().string();
                    parseInboxNum(json);
                } catch (IOException e) {
                    Log.v("brad", e.toString());
                }

            }
        }.start();
    }

    private void parseInboxNum(String json){
        try{
            inboxBoxNum = json;
            uiHandler.sendEmptyMessage(1);
        }catch(Exception e){

        }

    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: calendarView.setSchedules(schedules); break;
                case 1: inboxView.setText("收件匣(" + inboxBoxNum + ")");
            }

        }
    }


}
