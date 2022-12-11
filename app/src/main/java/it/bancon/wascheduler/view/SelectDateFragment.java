package it.bancon.wascheduler.view;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import it.bancon.wascheduler.R;

public class SelectDateFragment extends DialogFragment {
    CalendarView calendarView;
    Button buttonConfirm;
    SelectDateFragmentListener selectDateFragmentListener;
    Activity activity;

    public SelectDateFragment(Activity activity,SelectDateFragmentListener selectDateFragmentListener){
        this.activity = activity;
        this.selectDateFragmentListener = selectDateFragmentListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_data, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        buttonConfirm = (Button) getView().findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        calendarView = (CalendarView) getView().findViewById(R.id.calendarView);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        calendarView.setDate(currentTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String [] date = dtf.format(now).split("/");

        selectDateFragmentListener.OnDateChanged(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectDateFragmentListener.OnDateChanged(i,i1,i2);
            }
        });
    }

    public interface SelectDateFragmentListener{
        public void OnDateChanged(int year, int month, int dayOfMonth);
    }
}