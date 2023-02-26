package it.bancon.wascheduler.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.activity.MainAppActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


public class UpdateScheduleConfirmationDialogFragment extends DialogFragment {
    private Context context;
    private Button confirmUpdateButton;
    private Button denyUpdateButton;
    private  UpdateScheduleListener listener;


    public UpdateScheduleConfirmationDialogFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_schedule_confirmation_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);

        confirmUpdateButton = getDialog().findViewById(R.id.buttonYesUpdate);
        denyUpdateButton = getDialog().findViewById(R.id.buttonNoUpdate);

        confirmUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listener.onConfirmationUpdateSchedule();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, MainAppActivity.class);
                startActivity(intent);
                dismiss();
            }
        });

        denyUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainAppActivity.class);
                startActivity(intent);
                dismiss();
            }
        });
    }

    public interface UpdateScheduleListener {
        void onConfirmationUpdateSchedule() throws IOException;
    }


}