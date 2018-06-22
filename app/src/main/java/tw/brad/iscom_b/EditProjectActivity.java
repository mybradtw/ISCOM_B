package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProjectActivity extends AppCompatActivity {
    private MainApp mainApp;
    private EditDetailFragment editDetailFragment;
    private EditHistFragment editHistFragment;
    private EditFeeFragment editFeeFragment;
    private FragmentManager fragmentManager;
    private View detailBtn, histBtn, feeBtn;

    private TextView[] titles = new TextView[4];
    private ImageView[] icons = new ImageView[4];
    private int[] imgIcons = {R.drawable.edit_icon0,R.drawable.edit_icon1,
            R.drawable.edit_icon2,R.drawable.edit_icon3};
    private int[] imgCIcons = {R.drawable.edit_icon0c,R.drawable.edit_icon1c,
            R.drawable.edit_icon2c,R.drawable.edit_icon3c};

    private int click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        mainApp = (MainApp)getApplication();
        init();
    }

    private void init(){
        titles[0] = findViewById(R.id.edit_title0);
        titles[1] = findViewById(R.id.edit_title1);
        titles[2] = findViewById(R.id.edit_title2);
        titles[3] = findViewById(R.id.edit_title3);

        icons[0] = findViewById(R.id.edit_icon0);
        icons[1] = findViewById(R.id.edit_icon1);
        icons[2] = findViewById(R.id.edit_icon2);
        icons[3] = findViewById(R.id.edit_icon3);

        editDetailFragment = new EditDetailFragment();
        editHistFragment = new EditHistFragment();
        editFeeFragment = new EditFeeFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.edit_container, editDetailFragment);
        transaction.commit();

        click = 0; setTabIcon();

        detailBtn = findViewById(R.id.edit_detail);
        histBtn = findViewById(R.id.edit_hist);
        feeBtn = findViewById(R.id.edit_fee);

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDetail();
            }
        });
        histBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHist();
            }
        });
        feeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFee();
            }
        });

    }
    private void setTabIcon(){
        for(int i=0; i<icons.length; i++){
            icons[i].setImageResource(imgIcons[i]);
        }
        icons[click].setImageResource(imgCIcons[click]);
    }

    private void gotoDetail(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.edit_container, editDetailFragment);
        transaction.commit();
        click = 0; setTabIcon();

    }

    private void gotoHist(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.edit_container, editHistFragment);
        transaction.commit();
        click = 1; setTabIcon();
    }

    private void gotoFee(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.edit_container, editFeeFragment);
        transaction.commit();
        click = 2; setTabIcon();
    }

}
