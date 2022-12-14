package it.bancon.wascheduler;

import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.model.ContactLoader;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.validator.ScheduleValidatorForm;
import it.bancon.wascheduler.view.CompletedFragment;
import it.bancon.wascheduler.view.SelectDateFragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewScheduleActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, SelectDateFragment.SelectDateFragmentListener {
    private TextView textTimeSelected;
    private TextView textDateSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContact;
    private EditText editTextMessage;
    private SelectDateFragment selectDateFragment;
    private CompletedFragment completedFragment;
    private AutoCompleteTextView editTextPhone;
    private ArrayList<ContactModel> contacts;
    private SelectDateFragment.SelectDateFragmentListener listener = this;
    private int hour,minute;
    private int day,month,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        getSupportActionBar().setTitle("Nuova schedulazione");
        contacts = new ArrayList<>();
        populateAutocomplete();
        editTextTitle = findViewById(R.id.editTextTitleSchedule);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextContact = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        textDateSelected = findViewById(R.id.textViewShowDateSelected);
        textTimeSelected = findViewById(R.id.textViewShowTimeSelected);
    }

    public void populateAutocomplete(){
        ContactLoader contactLoader = new ContactLoader(NewScheduleActivity.this,NewScheduleActivity.this);
        contactLoader.loadContactList();
        String [] namesToAdapter = contactLoader.getContactNames();

        editTextPhone = findViewById(R.id.editTextPhone);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,namesToAdapter);
        editTextPhone.setAdapter(adapter);
    }

    public void popCompletedMessage(View view) throws IOException {
        /*TO DO:
        -   INSERIRE VALIDAZIONE PER POI POTER VISUALIZZARE IL FRAGMENT
        -   SALVATAGGIO INFORMAZIONI IN UN FILE
        * */

        ScheduleValidatorForm validatorForm= new ScheduleValidatorForm(NewScheduleActivity.this,NewScheduleActivity.this,contacts);
        boolean resultValidation = validatorForm.validate();
        if(resultValidation) {
            saveNewSchedulation();
            completedFragment = new CompletedFragment(this);
            completedFragment.show(getSupportFragmentManager(),"completedFragment");
        }

    }

    private void saveNewSchedulation() throws IOException {

        SchedulationDetails schedulationDetails = new SchedulationDetails();
        schedulationDetails.setTitle(editTextTitle.getText().toString());
        schedulationDetails.setDescription(editTextDescription.getText().toString());
        schedulationDetails.setMessage(editTextMessage.getText().toString());
        schedulationDetails.setDateToSchedule(textDateSelected.getText().toString().replace("\n"," ").trim());
        schedulationDetails.setHourToSchedule(textTimeSelected.getText().toString().replace("\n"," ").trim());
        schedulationDetails.setContacts(contacts);

        IOUtils.addScheduleProgramToFile(NewScheduleActivity.this,AppContractClass.FILE_NAME,schedulationDetails);
    }

    public void popTimePicker(View view){

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,this,hour,minute,true);
        timePickerDialog.setTitle("Seleziona ora");
        timePickerDialog.show();
    }

    public void popCalendarView(View view){
        selectDateFragment = new SelectDateFragment(NewScheduleActivity.this,listener);
        selectDateFragment.show(getSupportFragmentManager(),"selectDateFragment");
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