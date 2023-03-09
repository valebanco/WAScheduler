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
import it.bancon.wascheduler.view.SelectContactsFragment;
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
    - Implementare logica di modifica o aggiornamento della schedulazione:
        * vedere se la schedulazione è stata realmente modificata/aggiornata;
        * vedere come sovrascrivere i dati della schedulazione appena modificata/aggiornata;
    - Implementare logica di rimozione della schedulazione:
        * come sovrascrivere il file per la rimozione della schedulazione
        * decidere se è opportuno scegliere una base dati locale, invece del file di testo
 */
public class DetailsScheduleActivity extends AppCompatActivity
        implements UpdateScheduleConfirmationDialogFragment.UpdateScheduleListener,
        RemoveScheduleConfirmationDialogFragment.RemoveScheduleListener,
        TimePickerDialog.OnTimeSetListener,
        SelectDateFragment.SelectDateFragmentListener,
        SelectContactsFragment.onUpdateCountSelectedContactsListener{

    SchedulationDetails schedulationDetailsSelected;
    private SelectDateFragment selectDateFragment;
    private TextView textTimeSelected;
    private TextView textDateSelected;
    private TextView textCountContactsSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextMessage;


    private ArrayList<ContactModel> contacts;
    private UpdateScheduleConfirmationDialogFragment updateFragment;
    private RemoveScheduleConfirmationDialogFragment removeFragment;
    private SelectDateFragment.SelectDateFragmentListener listener = this;

    private int hour,minute;
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_schedule);
        getSupportActionBar().setTitle(getResources().getString(R.string.schedulation_details_title));
        editTextTitle = findViewById(R.id.editTextTitleSchedule);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextMessage = findViewById(R.id.editTextMessage);
        textDateSelected = findViewById(R.id.textViewShowDateSelected);
        textTimeSelected = findViewById(R.id.textViewShowTimeSelected);
        textCountContactsSelected = findViewById(R.id.textViewShowNumberAddedContacts);

        schedulationDetailsSelected  = getIntent().getParcelableExtra(AppContractClass.KEY_PARCELABLE_SCHEDULATION_DETAILS);
        contacts = (ArrayList<ContactModel>) schedulationDetailsSelected.getContacts();

        loadSchedulationDetailsInformationToActivity();

    }
    public void popSelectContactsFragment(View view){
        SelectContactsFragment selectContactsFragment = new SelectContactsFragment(DetailsScheduleActivity.this, DetailsScheduleActivity.this, contacts, this);
        selectContactsFragment.show(getSupportFragmentManager(),"selectContactFragment");
    }
    public void popTimePicker(View view) {

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,this,hour,minute,true);
        timePickerDialog.setTitle(R.string.select_hour_text);
        timePickerDialog.show();
    }

    public void popCalendarView(View view) {
        selectDateFragment = new SelectDateFragment(DetailsScheduleActivity.this,listener);
        selectDateFragment.show(getSupportFragmentManager(),"selectDateFragment");
    }


    private void loadSchedulationDetailsInformationToActivity() {
        editTextTitle.setText(schedulationDetailsSelected.getTitle());
        editTextDescription.setText(schedulationDetailsSelected.getDescription());
        editTextMessage.setText(schedulationDetailsSelected.getMessage());

        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());

        textTimeSelected.setText(schedulationDetailsSelected.getHourToSchedule());
        textDateSelected.setText(schedulationDetailsSelected.getDateToSchedule());
    }



    public void popUpdateConfirmationMessage(View view) throws IOException {
        ScheduleValidatorForm validatorForm= new ScheduleValidatorForm(DetailsScheduleActivity.this,DetailsScheduleActivity.this,contacts);
        boolean resultValidation = validatorForm.validate();
        if(resultValidation) {
            updateFragment = new UpdateScheduleConfirmationDialogFragment(this,this);
            updateFragment.show(getSupportFragmentManager(),"updateFragment");
        }

    }
    public void popRemoveConfirmationMessage(View view) throws IOException {

        removeFragment = new RemoveScheduleConfirmationDialogFragment(this,this);
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
        textTimeSelected.setText(getResources().getString(R.string.selected_hour_text) + String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
        textTimeSelected.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void OnDateChanged(int selectedYear, int selectedMonth, int selectedDay) {
        day = selectedDay;
        month = selectedMonth;
        year = selectedYear;
        textDateSelected.setText(getResources().getString(R.string.selected_date_text) + String.format(Locale.getDefault(),"%02d/%02d/%04d",day,month,year));
        textDateSelected.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onUpdateCountContactList() {
        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());
    }
}