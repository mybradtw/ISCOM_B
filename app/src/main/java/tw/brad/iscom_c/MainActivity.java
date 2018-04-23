package tw.brad.iscom_c;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private CalendarFragment calendarFragment;
    private MessageFragment messageFragment;
    private ProjectFragment projectFragment;
    private FeeFragment feeFragment;
    private OtherFragment otherFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        calendarFragment = new CalendarFragment();
        messageFragment = new MessageFragment();
        projectFragment = new ProjectFragment();
        feeFragment = new FeeFragment();
        otherFragment = new OtherFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, calendarFragment);
        transaction.commit();
    }

}
