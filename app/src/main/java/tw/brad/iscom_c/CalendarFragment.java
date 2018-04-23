package tw.brad.iscom_c;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CalendarFragment extends Fragment {
    private View mainView;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(
                    R.layout.fragment_calendar, container, false);
            calendarView = mainView.findViewById(R.id.calendar_cal);
//            calendarView.setYearMonth(2017, 7); // => 六週
            calendarView.setYearMonth(2018, 4);
        }
        return mainView;
    }

}
