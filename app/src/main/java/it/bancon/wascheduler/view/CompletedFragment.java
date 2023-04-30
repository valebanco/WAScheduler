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

import it.bancon.wascheduler.R;
import it.bancon.wascheduler.utils.ActivityNavigationUtils;


public class CompletedFragment extends DialogFragment {
    private Button buttonContinue;
    private final Activity activity;
    public CompletedFragment(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_completed, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        buttonContinue = getDialog().findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityNavigationUtils.goUpToTopActivity(activity);
                dismiss();
            }
        });
    }
}