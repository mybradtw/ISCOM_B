package tw.brad.iscom_c;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LastwordMyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword_my);
    }

    public void func1(View view) {
        Intent intent = new Intent(this, LastwordMyViewActivity.class);
        startActivity(intent);
    }

    public void func2(View view) {
        Intent intent = new Intent(this, LastwordMyDownloadActivity.class);
        startActivity(intent);
    }
    public void func3(View view) {
        Intent intent = new Intent(this, LastwordMyDownloadActivity.class);
        startActivity(intent);
    }
    public void func4(View view) {
    }
}
