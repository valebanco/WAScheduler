package it.bancon.wascheduler.activity;

import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactLoader;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.utils.IOUtils;
import it.bancon.wascheduler.validator.ScheduleValidatorForm;
import it.bancon.wascheduler.view.CompletedFragment;
import it.bancon.wascheduler.view.RemoveScheduleConfirmationDialogFragment;
import it.bancon.wascheduler.view.SelectDateFragment;
import it.bancon.wascheduler.view.UpdateScheduleConfirmationDialogFragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/*
TODO:
    - Modificare questa Activity in modo da essere coerente con la newScheduleActivity
    - Implementare logica di modifica o aggiornamento della schedulazione:
        * vedere se la schedulazione è stata realmente modificata/aggiornata;
        * vedere come sovrascrivere i dati della schedulazione appena modificata/aggiornata;
    - Implementare logica di rimozione della schedulazione:
        * come sovrascrivere il file per la rimozione della schedulazione
        * decidere se è opportuno scegliere una base dati locale, invece del file di testo
    - rivedere il caricamento dei contatti dall'oggetto Parcelable: ATTUALMENTE SBALLATO...
 */
public class DetailsScheduleActivity extends AppCompatActivity
        implements UpdateScheduleConfirmationDialogFragment.UpdateScheduleListener,
        RemoveScheduleConfirmationDialogFragment.RemoveScheduleListener,
        TimePickerDialog.OnTimeSetListener,
        SelectDateFragment.SelectDateFragmentListener{

    SchedulationDetails schedulationDetailsSelected;
    private SelectDateFragment selectDateFragment;
    private TextView textTimeSelected;
    private TextView textDateSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContact;
    private EditText editTextMessage;
    private AutoCompleteTextView editTextPhone;

    private List<ContactModel> contacts;
    private UpdateScheduleConfirmationDialogFragment updateFragment;
    private RemoveScheduleConfirmationDialogFragment removeFragment;
    private SelectDateFragment.SelectDateFragmentListener listener = this;

    private int hour,minute;
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_schedule);
        getSupportActionBar().setTitle("Dettagli schedulazione");
        populateAutocomplete();
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


    public void popTimePicker(View view) {

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,this,hour,minute,true);
        timePickerDialog.setTitle("Seleziona ora");
        timePickerDialog.show();
    }

    public void popCalendarView(View view) {
        selectDateFragment = new SelectDateFragment(DetailsScheduleActivity.this,listener);
        selectDateFragment.show(getSupportFragmentManager(),"selectDateFragment");
    }
    public void populateAutocomplete(){
        ContactLoader contactLoader = new ContactLoader(DetailsScheduleActivity.this,DetailsScheduleActivity.this);
        contactLoader.loadContactList();
        String [] namesToAdapter = contactLoader.getContactNames();

        editTextPhone = findViewById(R.id.editTextPhone);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,namesToAdapter);
        editTextPhone.setAdapter(adapter);

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


    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        hour = selectedHour;
        minute = selectedMinute;
        textTimeSelected.setText("Ora selezionata: \n" + String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
        textTimeSelected.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void OnDateChanged(int selectedYear, int selectedMonth, int selectedDay) {
        day = selectedDay;
        month = selectedMonth;
        year = selectedYear;
        textDateSelected.setText("Data selezionata: " + String.format(Locale.getDefault(),"%02d/%02d/%04d",day,month,year));
        textDateSelected.setTextColor(getResources().getColor(R.color.white));
    }
}