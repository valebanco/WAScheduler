package it.bancon.wascheduler.main;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.bancon.wascheduler.PermissionChecker;
import androidx.recyclerview.widget.RecyclerView;
import it.bancon.wascheduler.AppContractClass;
import it.bancon.wascheduler.IOUtils;
import it.bancon.wascheduler.NewScheduleActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.model.SchedulationDetailsPreview;
import it.bancon.wascheduler.view.AdapterListViewschedule;

public class MainAppActivity extends AppCompatActivity {

    FloatingActionButton FABAddScheduleProgram;
    TextView emptyListSchedulesMessage;
    AdapterListViewschedule adapter;
    ListView listViewSchedule;
    PermissionChecker permissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        listViewSchedule = findViewById(R.id.listViewSchedules);
        emptyListSchedulesMessage = findViewById(R.id.emptyListMessageTextView);

        permissionChecker = new PermissionChecker(MainAppActivity.this,MainAppActivity.this, Manifest.permission.READ_CONTACTS);
        if(permissionChecker.checkPermission(AppContractClass.MESSAGE_RATIONALE_READ_CONTACTS,AppContractClass.REQUEST_CODE_READ_CONTACTS)){
            //MI ESTRAGGO TUTTE LE SCHEDULAZIONI DA VISUALIZZARE NELL'ACTIVITY
            if(!IOUtils.isEmptyFile(MainAppActivity.this,"schedules.txt")){
                emptyListSchedulesMessage.setVisibility(View.GONE);
                try {
                    loadSchedulePrograms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




        FABAddScheduleProgram = findViewById(R.id.addScheduleFAB);

        FABAddScheduleProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissionChecker.hasPermission()){
                    Intent intent = new Intent(getApplicationContext(), NewScheduleActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainAppActivity.this,AppContractClass.MESSAGE_TOAST_NEED_READ_CONTACT_PERMISSION,Toast.LENGTH_SHORT);
                }


            }
        });


    }

    private void loadSchedulePrograms() throws IOException {
        List<SchedulationDetails> schedulationDetailsList = IOUtils.loadScheduleProgramsFromFile(MainAppActivity.this, AppContractClass.FILE_NAME);
        ArrayList<SchedulationDetailsPreview> schedulationDetailsPreviews = new ArrayList<>();
        SchedulationDetailsPreview itemPreview;

        for (SchedulationDetails item : schedulationDetailsList) {

            itemPreview = new SchedulationDetailsPreview();
            itemPreview.setTitle(item.getTitle());
            itemPreview.setDescription(item.getDescription());
            itemPreview.setDateToSchedule(item.getDateToSchedule());
            itemPreview.setHourToSchedule(item.getHourToSchedule());
            schedulationDetailsPreviews.add(itemPreview);
        }

        adapter = new AdapterListViewschedule(MainAppActivity.this,R.layout.program_schedule_preview_item,schedulationDetailsPreviews);
        listViewSchedule.setAdapter(adapter);
    }
}