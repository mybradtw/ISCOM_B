package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

public class BCalendarActivity extends AppCompatActivity {
    private BCalendarView calendarView;
    private int intYear, intMonth;
    private ListView eventList;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcalendar);

        intYear = Calendar.getInstance().get(Calendar.YEAR);
        intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        calendarView = findViewById(R.id.bcalendarView);
        calendarView.setYearMonth(intYear, intMonth);

        back = findViewById(R.id.bcalendar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        eventList = findViewById(R.id.bcalendar_eventlist);
        updateEventList(true);

    }

    private void updateEventList(boolean init){
        int[] to = {R.id.item_bcalendar_event_begintime, R.id.item_bcalendar_event_endtime,
                R.id.item_bcalendar_event_cont};
        String[] from = {"begintime","endtime","cont"};
        LinkedList<HashMap<String,String>> data = new LinkedList<>();
        HashMap<String,String> d0 = new HashMap<>();
        d0.put(from[0], "09:00");
        d0.put(from[1], "10:00");
        d0.put(from[2], "忙碌");
        data.add(d0);

        HashMap<String,String> d1 = new HashMap<>();
        d1.put(from[0], "09:00");
        d1.put(from[1], "10:00");
        d1.put(from[2], "忙碌");
        data.add(d1);

        SimpleAdapter adapter = new SimpleAdapter(this,
                data, R.layout.item_bcalendar_event, from,to);

        eventList.setAdapter(adapter);
    }

}
