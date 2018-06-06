package tw.brad.iscom_c;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LastwordMyDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastword_my_download);
    }

    public void back(View view) {
        finish();

    }

    public void send(View view) {
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("遺囑已寄送到：");
        builder.setMessage("test@test.com");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
