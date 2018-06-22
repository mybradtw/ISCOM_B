package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailScheduleActivity extends AppCompatActivity {
    private int month;
    private Schedule schedule;
    private TextView monthView, editView;
    private TextView titleView, placeView, startView, endView, typeView, nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);

        monthView = findViewById(R.id.detail_schedule_month);
        editView = findViewById(R.id.detail_schedule_edit);

        titleView = findViewById(R.id.detail_schedule_title);
        placeView = findViewById(R.id.detail_schedule_place);
        startView = findViewById(R.id.detail_schedule_start);
        endView = findViewById(R.id.detail_schedule_end);
        typeView = findViewById(R.id.detail_schedule_type);
        nameView = findViewById(R.id.detail_schedule_name);

        month = getIntent().getIntExtra("month", 0);
        monthView.setText(" " + month + "月");
        editView.setVisibility(
                getIntent().getBooleanExtra(
                        "isEditable",false)? View.VISIBLE:View.INVISIBLE);

        schedule = (Schedule) (getIntent().getSerializableExtra("schedule"));
        titleView.setText(schedule.meeting_title);
        placeView.setText(schedule.meeting_place);
        startView.setText(schedule.c_date_start);
        endView.setText(schedule.c_date_end);
        typeView.setText(schedule.p_class);
        nameView.setText(schedule.p_name);

    }
}
