package tw.brad.iscom_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText account, passwd;
    private MainApp mainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mainApp = (MainApp) getApplication();
        account = findViewById(R.id.login_account);
        passwd = findViewById(R.id.login_passwd);
    }

    public void login(View view) {
//        final String strAccount = account.getText().toString();
//        final String strPasswd = passwd.getText().toString();
        final String strAccount = "systemadmin";
        final String strPasswd = "123";

        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(BradUtils.apiUrl + "Login/" +
                        strAccount + "/" + strPasswd)
                        .build();
                try {
                    Response response = mainApp.client.newCall(request).execute();
                    parseLogin(response.body().string());
                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();


    }

    private void parseLogin(String json){
        try {
            JSONObject root = new JSONObject(json);
            boolean result = root.getBoolean("Result");
            mainApp.token = root.getString("token");
            Log.v("brad", "token = " + mainApp.token);
            if (result){
                gotoMain();
            }else{
                Log.v("brad", "login error");
            }
        }catch(Exception e){
            Log.v("brad", "parse error");
        }
    }

    private void gotoMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void reg(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
