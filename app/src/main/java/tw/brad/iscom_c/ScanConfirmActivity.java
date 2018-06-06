package tw.brad.iscom_c;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScanConfirmActivity extends AppCompatActivity {
    private TextView confirmResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_confirm);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        confirmResult = findViewById(R.id.qrcodeconfirm);
        confirmResult.setText(result);
    }
}
