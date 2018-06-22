package tw.brad.iscom_b;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditDetailFragment extends Fragment {
    private View mainView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null){
            mainView = inflater.inflate(R.layout.fragment_edit_detail, container, false);
            View updateView = mainView.findViewById(R.id.edit_detail_update);
            updateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update();
                }
            });
        }

        return mainView;
    }

    private void update(){

    }


}
