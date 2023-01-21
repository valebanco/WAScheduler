package it.bancon.wascheduler.activity;

import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.utils.IOUtils;
import it.bancon.wascheduler.validator.ScheduleValidatorForm;
import it.bancon.wascheduler.view.CompletedFragment;
import it.bancon.wascheduler.view.RemoveScheduleConfirmationDialogFragment;
import it.bancon.wascheduler.view.UpdateScheduleConfirmationDialogFragment;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsScheduleActivity extends AppCompatActivity
        implements UpdateScheduleConfirmationDialogFragment.UpdateScheduleListener,
        RemoveScheduleConfirmationDialogFragment.RemoveScheduleListener {
    SchedulationDetails schedulationDetailsSelected;
    private TextView textTimeSelected;
    private TextView textDateSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContact;
    private EditText editTextMessage;

    private List<ContactModel> contacts;
    private UpdateScheduleConfirmationDialogFragment updateFragment;
    private RemoveScheduleConfirmationDialogFragment removeFragment;

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

        contacts = new ArrayList<>();

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

    public void popUpdateConfirmationMessage(View view) throws IOException {
        ScheduleValidatorForm validatorForm= new ScheduleValidatorForm(DetailsScheduleActivity.this,DetailsScheduleActivity.this,contacts);
        boolean resultValidation = validatorForm.validate();
        if(resultValidation) {
            updateFragment = new UpdateScheduleConfirmationDialogFragment(this);
            updateFragment.show(getSupportFragmentManager(),"updateFragment");
        }

    }
    public void popRemoveConfirmationMessage(View view) throws IOException {

        removeFragment = new RemoveScheduleConfirmationDialogFragment(this);
        removeFragment.show(getSupportFragmentManager(),"removeFragment");

    }

    private void updateSchedulation() throws IOException {
        //INTRODURRE LA LOGICA DI MODIFICA DELLA SCHEDULAZIONE
    }

    private void removeSchedulation() {
        //INTRODURRE LA LOGICA DI RIMOZIONE DELLA SCHEDULAZIONE
        //NOTA: nella rimozione ci vuole l'id alla schedulazione
    }


    @Override
    public void onConfirmationUpdateSchedule() throws IOException {
        updateSchedulation();
    }

    @Override
    public void onConfirmationRemoveSchedule() throws IOException {
        removeSchedulation();
    }


}