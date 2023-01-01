package it.bancon.wascheduler.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.bancon.wascheduler.model.Schedulation;
import it.bancon.wascheduler.model.SchedulationDetailsPreview;
import it.bancon.wascheduler.R;

public class AdapterListViewschedule extends ArrayAdapter<SchedulationDetailsPreview> {
    private Context mContext;
    private int mResource;

    public AdapterListViewschedule(@NonNull Context context, int resource, @NonNull List<SchedulationDetailsPreview> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView textViewTitle = convertView.findViewById(R.id.TextTitleScheduleProgram);
        TextView textViewDescription = convertView.findViewById(R.id.TextDescriptionScheduleProgram);
        TextView textViewDate = convertView.findViewById(R.id.TextSendDate);
        TextView textViewTime = convertView.findViewById(R.id.TextSendHour);

        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();
        String date = getItem(position).getDateToSchedule();
        String time = getItem(position).getHourToSchedule();

        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewDate.setText(date);
        textViewTime.setText(time);



        return convertView;
    }
}
