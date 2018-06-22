package tw.brad.iscom_b;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OtherFragment extends Fragment {
    private View mainView;
    private View viewChat, viewCList, viewAccount, viewLastword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_other, container, false);
            viewChat = mainView.findViewById(R.id.other_chat);
            viewCList = mainView.findViewById(R.id.other_clist);
            viewAccount = mainView.findViewById(R.id.other_account);
            viewLastword = mainView.findViewById(R.id.other_lastword);

            viewChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoChat();
                }
            });

            viewCList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoCustList();
                }
            });
            viewAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //gotoAddValue();
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

    private void gotoCustList(){
        Intent intent = new Intent(getContext(), CustListActivity.class);
        startActivity(intent);
    }

    private void gotoLastword(){
        Intent intent = new Intent(getContext(), LastwordActivity.class);
        startActivity(intent);
    }
}
