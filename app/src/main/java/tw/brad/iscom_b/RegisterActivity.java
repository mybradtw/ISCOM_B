package tw.brad.iscom_b;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText account, passwd, name, id, address, tel, mobile, email;
    private final OkHttpClient client = new OkHttpClient();
    private MainApp mainApp;
    private UIHandler uiHandler;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mainApp = (MainApp) getApplication();

        id = findViewById(R.id.register_id);
        account = findViewById(R.id.register_account);
        passwd = findViewById(R.id.register_passwd);
        name = findViewById(R.id.register_name);
        address = findViewById(R.id.register_address);
        tel = findViewById(R.id.register_tel);
        mobile = findViewById(R.id.register_mobile);
        email = findViewById(R.id.register_email);

        uiHandler = new UIHandler();
    }

    public void send(View view) {
        new Thread(){
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("M_SN", id.getText().toString());
                builder.add("M_ACCOUNT", account.getText().toString());
                builder.add("M_PASSWORD", passwd.getText().toString());
                builder.add("M_NAME", name.getText().toString());
                builder.add("M_TYPE", "C");
                builder.add("M_GENDER", "M");
                builder.add("M_ADDRESS", address.getText().toString());
                builder.add("M_TEL_D", tel.getText().toString());
                builder.add("M_MAIL", email.getText().toString());
                FormBody formBody = builder.build();

                Request request = new Request.Builder()
                        .url(BradUtils.apiUrl + "AddCustomer/")
                        .header("Authorization", "Bearer " + mainApp.token)                        .post(formBody)
                        .build();

                try {
                    Response response =  client.newCall(request).execute();
                    String json = response.body().string();
                    //Log.v("brad", json);
                    // 已建立  brad/123
                    //{"result":true,"msg":""}
                    //{"result":false,"msg":"資料建立過程發生錯誤"}
                    parseRegResult(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void parseRegResult(String json){
        try {
            JSONObject root = new JSONObject(json);
            boolean isOk = root.getBoolean("result");
            if (isOk){
                message = "註冊成功";
                uiHandler.sendEmptyMessage(0);
                finish();
            }else{
                String msg = root.getString("msg");
                message = msg;
                uiHandler.sendEmptyMessage(0);
            }
        }catch(Exception e){
            message = "api parse error";
            uiHandler.sendEmptyMessage(0);
        }
    }
    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT)
                    .show();

        }
    }
}
