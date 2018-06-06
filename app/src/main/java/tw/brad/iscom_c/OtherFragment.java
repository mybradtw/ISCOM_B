package tw.brad.iscom_c;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OtherFragment extends Fragment {
    private View mainView;
    private View viewChat, viewScan, viewAddValue, viewLastword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_other, container, false);
            viewChat = mainView.findViewById(R.id.other_chat);
            viewScan = mainView.findViewById(R.id.other_scan);
            viewAddValue = mainView.findViewById(R.id.other_addvalue);
            viewLastword = mainView.findViewById(R.id.other_lastword);

            viewChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoChat();
                }
            });

            viewScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoScan();
                }
            });
            viewAddValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoAddValue();
                }
            });
            viewLastword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoLastword();
                }
            });

        }
        return mainView;
    }

    private void gotoChat(){
        Intent intent = new Intent(getContext(), ChatListActivity.class);
        startActivity(intent);
    }

    private void gotoScan(){
        Intent intent = new Intent(getContext(), ScanListActivity.class);
        startActivity(intent);
    }

    private void gotoAddValue(){
        Intent intent = new Intent(getContext(), AddValueMainActivity.class);
        startActivity(intent);
    }

    private void gotoLastword(){
        Intent intent = new Intent(getContext(), LastwordActivity.class);
        startActivity(intent);
    }
}
