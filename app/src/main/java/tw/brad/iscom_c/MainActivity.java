package tw.brad.iscom_c;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MainApp mainApp;
    private CalendarFragment calendarFragment;
    private MessageFragment messageFragment;
    private ProjectFragment projectFragment;
    private FeeFragment feeFragment;
    private OtherFragment otherFragment;
    private FragmentManager fragmentManager;
    private View calendarBtn, messageBtn, projectBtn, feeBtn, otherBtn;

    private TextView[] titles = new TextView[5];
    private ImageView[] icons = new ImageView[5];
    private int[] imgIcons = {R.drawable.main_icon0,R.drawable.main_icon1,
            R.drawable.main_icon2,R.drawable.main_icon3,R.drawable.main_icon4};
    private int[] imgCIcons = {R.drawable.main_icon0c,R.drawable.main_icon1c,
            R.drawable.main_icon2c,R.drawable.main_icon3c,R.drawable.main_icon4c};

    private int click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApp = (MainApp)getApplication();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }else{
            mainApp.canCamera = true;
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mainApp.canCamera = true;
            init();
        }
    }

    private void init(){
        titles[0] = findViewById(R.id.main_title0);
        titles[1] = findViewById(R.id.main_title1);
        titles[2] = findViewById(R.id.main_title2);
        titles[3] = findViewById(R.id.main_title3);
        titles[4] = findViewById(R.id.main_title4);

        icons[0] = findViewById(R.id.main_icon0);
        icons[1] = findViewById(R.id.main_icon1);
        icons[2] = findViewById(R.id.main_icon2);
        icons[3] = findViewById(R.id.main_icon3);
        icons[4] = findViewById(R.id.main_icon4);

        calendarFragment = new CalendarFragment();
        messageFragment = new MessageFragment();
        projectFragment = new ProjectFragment();
        feeFragment = new FeeFragment();
        otherFragment = new OtherFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, calendarFragment);
        transaction.commit();

        click = 0; setTabIcon();

        calendarBtn = findViewById(R.id.main_calendar);
        messageBtn = findViewById(R.id.main_message);
        projectBtn = findViewById(R.id.main_project);
        feeBtn = findViewById(R.id.main_fee);
        otherBtn = findViewById(R.id.main_other);

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCalendar();
            }
        });
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMessage();
            }
        });
        projectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoProject();
            }
        });
        feeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFee();
            }
        });
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOther();
            }
        });
    }

    private void setTabIcon(){
        for(int i=0; i<icons.length; i++){
            icons[i].setImageResource(imgIcons[i]);
        }
        icons[click].setImageResource(imgCIcons[click]);
    }

    private void gotoCalendar(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, calendarFragment);
        transaction.commit();
        click = 0; setTabIcon();

    }

    private void gotoMessage(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, messageFragment);
        transaction.commit();
        click = 1; setTabIcon();
    }

    private void gotoProject(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, projectFragment);
        transaction.commit();
        click = 2; setTabIcon();
    }

    private void gotoFee(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, feeFragment);
        transaction.commit();
        click = 3; setTabIcon();
    }

    private void gotoOther(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, otherFragment);
        transaction.commit();
        click = 4; setTabIcon();
    }

}
