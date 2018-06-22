package tw.brad.iscom_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LastwordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword);
    }

    public void func1(View view) {
        Intent intent = new Intent(this, LastwordBuyRecordActivity.class);
        startActivity(intent);
    }

    public void func2(View view) {
    }

    public void func3(View view) {
        Intent intent = new Intent(this, LastwordBuyRecord2Activity.class);
        startActivity(intent);
    }

    public void func4(View view) {
        Intent intent = new Intent(this, LastwordMyActivity.class);
        startActivity(intent);
    }

}
