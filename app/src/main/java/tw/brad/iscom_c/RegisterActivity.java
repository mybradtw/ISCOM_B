package tw.brad.iscom_c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText account, passwd, name, id, address, tel, mobile, email;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account = findViewById(R.id.register_account);
        passwd = findViewById(R.id.register_passwd);
        name = findViewById(R.id.register_name);
        id = findViewById(R.id.register_id);
        address = findViewById(R.id.register_address);
        tel = findViewById(R.id.register_tel);
        mobile = findViewById(R.id.register_mobile);
    }

    public void send(View view) {
        new Thread(){
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("account", "brad");
                builder.add("passwd", "7654321");
                FormBody formBody = builder.build();

                Request request = new Request.Builder()
                        .url("http://edu.iscom.com.tw:2039/API/api/lawyer_WebAPI/")
                        .post(formBody)
                        .build();

                try {
                    Response response =  client.newCall(request).execute();
                    Log.v("brad", response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
