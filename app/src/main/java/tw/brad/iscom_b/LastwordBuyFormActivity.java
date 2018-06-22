package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LastwordBuyFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword_buy_form);
    }

    public void back(View view) {
        finish();
    }
    public void buy(View view) {
    }

}
