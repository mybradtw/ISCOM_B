package tw.brad.iscom_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SearchCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_calendar);
    }

    public void back(View view) {
        finish();
    }

    public void startSearch(View view) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
    }
}
