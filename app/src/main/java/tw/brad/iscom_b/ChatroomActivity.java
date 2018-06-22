package tw.brad.iscom_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.scaledrone.lib.Scaledrone;

import java.util.HashMap;
import java.util.LinkedList;

public class ChatroomActivity extends AppCompatActivity{
    private ListView list;
    private EditText editText;
    private Scaledrone scaledrone;
    private LinkedList<HashMap<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        editText = findViewById(R.id.chatroom_mesg);
        list = findViewById(R.id.chatroom_list);
        initListView();
    }

    private void initListView(){
        data = new LinkedList<>();
        HashMap<String,String> d0 = new HashMap<>();
        d0.put("who", "my");
        d0.put("mesg", "你好");
        data.add(d0);

        HashMap<String,String> d1 = new HashMap<>();
        d1.put("who", "their");
        d1.put("mesg", "Hi");
        data.add(d1);

        HashMap<String,String> d2 = new HashMap<>();
        d2.put("who", "my");
        d2.put("mesg", "請問...\n測試多列");
        data.add(d2);


        list.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HashMap<String,String> itemData = data.get(i);
            View itemView = null;
            if (itemData.get("who").equals("their")){
                itemView = LayoutInflater.from(ChatroomActivity.this)
                        .inflate(R.layout.chatroom_their_mesg,
                                null, false);

            }else if(itemData.get("who").equals("my")){
                itemView = LayoutInflater.from(ChatroomActivity.this)
                        .inflate(R.layout.chatroom_my_mesg,
                                null, false);

            }
            TextView mesg = itemView.findViewById(R.id.message_body);
            mesg.setText(itemData.get("mesg"));

            return itemView;
        }
    }


}
