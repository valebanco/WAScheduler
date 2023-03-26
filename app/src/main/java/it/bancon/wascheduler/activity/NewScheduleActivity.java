package it.bancon.wascheduler.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;
import it.bancon.wascheduler.utils.DateTimeUtils;
import it.bancon.wascheduler.utils.IOUtils;
import it.bancon.wascheduler.validator.ScheduleAddingValidator;
import it.bancon.wascheduler.validator.ScheduleValidatorForm;
import it.bancon.wascheduler.view.CompletedFragment;
import it.bancon.wascheduler.view.SelectContactsFragment;
import it.bancon.wascheduler.view.SelectDateFragment;



import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewScheduleActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener,
        SelectDateFragment.SelectDateFragmentListener,
        SelectContactsFragment.onUpdateCountSelectedContactsListener {
    private TextView textTimeSelected;
    private TextView textDateSelected;
    private TextView textCountContactsSelected;

    private String dateSelected;
    private String timeSelected;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextContact;
    private EditText editTextMessage;

    private SelectDateFragment selectDateFragment;
    private SelectContactsFragment selectContactsFragment;
    private CompletedFragment completedFragment;
    private TimePickerDialog timePickerDialog;

    private ArrayList<ContactModel> contacts;
    private SelectDateFragment.SelectDateFragmentListener listener = this;

    private int hour,minute;
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_new_schedule_activity);

        contacts = new ArrayList<>();

        editTextTitle = findViewById(R.id.editTextTitleSchedule);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextContact = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        textDateSelected = findViewById(R.id.textViewShowDateSelected);
        textTimeSelected = findViewById(R.id.textViewShowTimeSelected);
        textCountContactsSelected = findViewById(R.id.textViewShowNumberAddedContacts);
        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());
    }



    public void popCompletedMessage(View view) throws IOException {

        SchedulationDetails schedulationToBeAdded = buildSchedulationToBeAdded();

        boolean isValidDataInFormInserted = isValidDataInFormInserted();
        boolean isSchedulationAlreadyExisting = isSchedulationAlreadyExisting(schedulationToBeAdded);

        if(isValidDataInFormInserted && !isSchedulationAlreadyExisting) {

            saveNewSchedulation(schedulationToBeAdded);
            completedFragment = new CompletedFragment(this);
            completedFragment.show(getSupportFragmentManager(),"completedFragment");

        }

    }

    private SchedulationDetails buildSchedulationToBeAdded() throws IOException {
        SchedulationDetails schedulationDetails = new SchedulationDetails();
        String [] timeSelectedText = textTimeSelected.getText().toString().replace("\n"," ").trim().split(":");
        String [] dateSelectedText = textDateSelected.getText().toString().replace("\n"," ").trim().split(":");

        schedulationDetails.setId(IOUtils.generateId(NewScheduleActivity.this, AppContractClass.FILE_NAME));
        schedulationDetails.setTitle(editTextTitle.getText().toString());
        schedulationDetails.setDescription(editTextDescription.getText().toString());
        schedulationDetails.setMessage(editTextMessage.getText().toString());
        schedulationDetails.setDateToSchedule(dateSelectedText[1]);
        schedulationDetails.setHourToSchedule(timeSelectedText[1] + ":" + timeSelectedText[2]);
        schedulationDetails.setContacts(contacts);

        return schedulationDetails;
    }

    private boolean isSchedulationAlreadyExisting(SchedulationDetails schedulationToBeAdded) throws IOException {

        if(!IOUtils.isEmptyFile(NewScheduleActivity.this,"schedules.txt")) {

            List<SchedulationDetails> schedulationDetailsList = IOUtils.loadScheduleProgramsFromFile(this,AppContractClass.FILE_NAME);
            ScheduleAddingValidator validatorAddingNewSchedule = new ScheduleAddingValidator(NewScheduleActivity.this,NewScheduleActivity.this,schedulationDetailsList,schedulationToBeAdded);
            return validatorAddingNewSchedule.validate();

        } else {
            return false;
        }

    }

    private boolean isValidDataInFormInserted(){
        ScheduleValidatorForm validatorForm= new ScheduleValidatorForm(NewScheduleActivity.this,NewScheduleActivity.this,contacts);
        return validatorForm.validate();
    }

    private void saveNewSchedulation(SchedulationDetails schedulationToBeAdded) throws IOException {
        IOUtils.addScheduleProgramToFile(NewScheduleActivity.this, AppContractClass.FILE_NAME,schedulationToBeAdded);
    }

    public void popTimePicker(View view) {
        if(!textDateSelected.getText().toString().equals(getResources().getString(R.string.no_date_selected))) {
            int style = AlertDialog.THEME_HOLO_DARK;
            timePickerDialog = new TimePickerDialog(this,style,this,hour,minute,true);
            timePickerDialog.setTitle(R.string.select_hour_text);
            timePickerDialog.show();
        } else {
            Toast.makeText(NewScheduleActivity.this,getResources().getString(R.string.error_message_no_date_selected),Toast.LENGTH_SHORT).show();
        }

    }

    public void popCalendarView(View view) {
        selectDateFragment = new SelectDateFragment(NewScheduleActivity.this,listener);
        selectDateFragment.show(getSupportFragmentManager(),"selectDateFragment");
    }
    public void popSelectContactsFragment(View view){
        selectContactsFragment = new SelectContactsFragment(NewScheduleActivity.this,NewScheduleActivity.this,contacts,this);
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
            Toast.makeText(NewScheduleActivity.this,wrogTimeMessage,Toast.LENGTH_SHORT).show();

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
    }


    @Override
    public void onUpdateCountContactList() {
        textCountContactsSelected.setText(getResources().getString(R.string.selected_contacts_text) + contacts.size());
    }
}