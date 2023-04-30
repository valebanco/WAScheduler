package it.bancon.wascheduler.activity;

import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.utils.DateTimeUtils;
import it.bancon.wascheduler.validator.ScheduleValidatorForm;
import it.bancon.wascheduler.view.RemoveScheduleConfirmationDialogFragment;
import it.bancon.wascheduler.view.SelectContactsFragment;
import it.bancon.wascheduler.view.SelectDateFragment;
import it.bancon.wascheduler.view.UpdateScheduleConfirmationDialogFragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
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
    SchedulationDetails schedulationDetailsSelectedCopy;
    private SelectDateFragment selectDateFragment;
    private TextView textTimeSelected;
    private TextView textDateSelected;
    private TextView textCountContactsSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextMessage;

    private String timeSelected;
    private String dateSelected;

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

        schedulationDetailsSelectedCopy = getIntent().getParcelableExtra(AppContractClass.KEY_PARCELABLE_SCHEDULATION_DETAILS);
        schedulationDetailsSelected  = getIntent().getParcelableExtra(AppContractClass.KEY_PARCELABLE_SCHEDULATION_DETAILS);
        contacts = (ArrayList<ContactModel>) schedulationDetailsSelected.getContacts();

        loadSchedulationDetailsInformationToActivity();

    }




    private void loadSchedulationDetailsInformationToActivity() {
        editTextTitle.setText(schedulationDetailsSelected.getTitle());
        editTextDescription.setText(schedulationDetailsSelected.getDescription());
        editTextMessage.setText(schedulationDetailsSelected.getMessage());

        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());

        textTimeSelected.setText(getResources().getString(R.string.selected_hour_text) + schedulationDetailsSelected.getHourToSchedule());
        textDateSelected.setText(getResources().getString(R.string.selected_date_text) + schedulationDetailsSelected.getDateToSchedule());
    }



    public void popUpdateConfirmationMessage(View view) {
        ScheduleValidatorForm validatorForm= new ScheduleValidatorForm(DetailsScheduleActivity.this,DetailsScheduleActivity.this,contacts);
        boolean resultValidation = validatorForm.validate();
        if(resultValidation) {
            updateFragment = new UpdateScheduleConfirmationDialogFragment(this,this,this);
            updateFragment.show(getSupportFragmentManager(),"updateFragment");
        }

    }
    public void popRemoveConfirmationMessage(View view) throws IOException {

        removeFragment = new RemoveScheduleConfirmationDialogFragment(this,this,this);
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
    public void onConfirmationRemoveSchedule() {
        removeSchedulation();
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

    public void popSelectContactsFragment(View view){
        SelectContactsFragment selectContactsFragment = new SelectContactsFragment(DetailsScheduleActivity.this, DetailsScheduleActivity.this, contacts, this);
        selectContactsFragment.show(getSupportFragmentManager(),"selectContactFragment");
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        hour = selectedHour;
        minute = selectedMinute;
        timeSelected = String.format(Locale.getDefault(),"%02d:%02d",hour,minute);

        if(DateTimeUtils.isTodayDateEqualsToSelectedDate(dateSelected)
                && !DateTimeUtils.isTimeSelectedAfterNowTime(hour,minute)) {

            String wrogTimeMessage = getResources().getString(R.string.wrog_time_selected);
            Toast.makeText(DetailsScheduleActivity.this,wrogTimeMessage,Toast.LENGTH_SHORT).show();

        } else {
            textTimeSelected.setText(getResources().getString(R.string.selected_hour_text) + timeSelected);
            textTimeSelected.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void OnDateChanged(int selectedYear, int selectedMonth, int selectedDay) {
        day = selectedDay;
        month = selectedMonth;
        year = selectedYear;
        System.out.println(day +"-"+month +"-"+ year + "-->SELECTED");
        dateSelected = String.format(Locale.getDefault(),"%02d/%02d/%04d",day,month,year);
        textDateSelected.setText(getResources().getString(R.string.selected_date_text) + dateSelected);
        textDateSelected.setTextColor(getResources().getColor(R.color.white));
        textTimeSelected.setText(getResources().getString(R.string.no_time_selected));
    }

    @Override
    public void onUpdateCountContactList() {
        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());
    }
}