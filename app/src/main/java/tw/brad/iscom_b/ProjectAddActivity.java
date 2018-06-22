package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ProjectAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_add);
    }

    public void cancel(View view) {
        finish();
    }
    public void add(View view) {
        finish();
    }
}
