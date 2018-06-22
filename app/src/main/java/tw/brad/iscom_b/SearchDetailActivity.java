package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SearchDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
    }

    public void back(View view) {
        finish();
    }
}
