package tw.brad.iscom_b;

import android.app.Application;

import okhttp3.OkHttpClient;

public class MainApp extends Application {
    public boolean canCamera = false;
    public final OkHttpClient client = new OkHttpClient();
    public String token = null;
}
