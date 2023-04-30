package it.bancon.wascheduler.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.activity.DetailsScheduleActivity;
import it.bancon.wascheduler.activity.MainAppActivity;
import it.bancon.wascheduler.utils.ActivityNavigationUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

public class RemoveScheduleConfirmationDialogFragment extends DialogFragment {
    private Context context;
    private Activity activity;
    private Button confirmRemoveButton;
    private Button denyRemoveButton;
    private RemoveScheduleListener listener;

    public RemoveScheduleConfirmationDialogFragment(Activity activity,Context context, RemoveScheduleListener listener) {
        this.context = context;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_remove_schedule_confirmation_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);

        confirmRemoveButton = getDialog().findViewById(R.id.buttonYesRemove);
        denyRemoveButton = getDialog().findViewById(R.id.buttonNoRemove);

        confirmRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listener.onConfirmationRemoveSchedule();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ActivityNavigationUtils.goUpToTopActivity(activity);
                dismiss();
            }
        });

        denyRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityNavigationUtils.goUpToTopActivity(activity);
                dismiss();
            }
        });
    }

    public interface RemoveScheduleListener {
        void onConfirmationRemoveSchedule() throws IOException;
    }

}