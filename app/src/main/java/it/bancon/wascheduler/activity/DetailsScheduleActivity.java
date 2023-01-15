package it.bancon.wascheduler.activity;

import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsScheduleActivity extends AppCompatActivity {
    SchedulationDetails schedulationDetailsSelected;
    private TextView textTimeSelected;
    private TextView textDateSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContact;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_schedule);
        getSupportActionBar().setTitle("Dettagli schedulazione");
        editTextTitle = findViewById(R.id.editTextTitleSchedule);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextContact = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        textDateSelected = findViewById(R.id.textViewShowDateSelected);
        textTimeSelected = findViewById(R.id.textViewShowTimeSelected);

        schedulationDetailsSelected  = getIntent().getParcelableExtra(AppContractClass.KEY_PARCELABLE_SCHEDULATION_DETAILS);

        loadSchedulationDetailsInformationToActivity();

    }

    private void loadSchedulationDetailsInformationToActivity() {
        editTextTitle.setText(schedulationDetailsSelected.getTitle());
        editTextDescription.setText(schedulationDetailsSelected.getDescription());
        editTextMessage.setText(schedulationDetailsSelected.getMessage());

        String contactPlaceHolder = loadTextToContactsEditText();
        editTextContact.setText(contactPlaceHolder);

        textTimeSelected.setText(schedulationDetailsSelected.getHourToSchedule());
        textDateSelected.setText(schedulationDetailsSelected.getDateToSchedule());
    }

    private String loadTextToContactsEditText() {
        String resultString = "";

        for (ContactModel itemContact : schedulationDetailsSelected.getContacts()){
            resultString = resultString + itemContact.getName() + ";";
        }

        return resultString;
    }
}