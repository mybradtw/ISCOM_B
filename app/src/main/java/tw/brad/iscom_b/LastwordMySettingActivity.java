package tw.brad.iscom_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LastwordMySettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword_my_setting);
    }

    public void change(View view) {
    }

    public void view(View view) {
        Intent intent =
                new Intent(this, LastwordMySettingListActivity.class);
        startActivity(intent);
    }
}
