package tw.brad.iscom_b;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class AddScheduleActivity extends AppCompatActivity {
    private View add, cancel;
    private Dialog dialogConflictEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        add = findViewById(R.id.addSchedule_add);
        cancel = findViewById(R.id.addSchedule_cancel);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelEvent();
            }
        });

    }

    private void addEvent(){
        dialogConflictEvent = new Dialog(this);
        dialogConflictEvent.setContentView(R.layout.dialog_after_addevent);
        View add = dialogConflictEvent.findViewById(R.id.after_conflict);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConflictEvent.dismiss();
                gotoEditSchedule();
            }
        });

        // 使對話筐變大
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogConflictEvent.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogConflictEvent.getWindow().setAttributes(lp);
        dialogConflictEvent.show();

    }

    private void cancelEvent(){
        finish();
    }

    private void gotoEditSchedule(){
        Intent intent = new Intent(this, DetailScheduleActivity.class);
        startActivity(intent);
    }



}
