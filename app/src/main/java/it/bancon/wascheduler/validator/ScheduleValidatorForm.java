package it.bancon.wascheduler.validator;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.bancon.wascheduler.R;
import it.bancon.wascheduler.model.ContactLoader;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;

public class ScheduleValidatorForm implements Validator{
   private static final String EMPTY= "";
   private static final String ERROR_MESSAGE_EMPTY = "Questo campo non può essere vuoto";
   private static final String ERROR_MESSAGE_INVALID_CONTACT = "Contatto non valido";
   private static final String ERROR_MESSAGE_NO_DATE_SELECTED = "nessuna data di inoltro aggiunta";
   private static final String ERROR_MESSAGE_NO_TIME_SELECTED = "nessuna ora di inoltro aggiunta";
   private static final String NO_DATE_SELECTED = "Data selezionata: nessuna";
   private static final String NO_TIME_SELECTED = "Ora selezionata: nessuna";
   private String message;
   private Activity activity;
   private Context context;
   private List<ContactModel> contactModels;

   public ScheduleValidatorForm(Activity activity, Context context, List<ContactModel> contactModels){
      this.activity = activity;
      this.context = context;
      this.contactModels = contactModels;
   }

   @Override
   public boolean validate() {
      boolean result = true;
      ContactLoader contactLoader = new ContactLoader(activity,context);
      contactLoader.loadContactList();

      EditText editTextTitle = activity.findViewById(R.id.editTextTitleSchedule);
      EditText editTextMessage = activity.findViewById(R.id.editTextMessage);
      EditText editTextPhone = activity.findViewById(R.id.editTextPhone);
      TextView textViewDate = activity.findViewById(R.id.textViewShowDateSelected);
      TextView textViewTime = activity.findViewById(R.id.textViewShowTimeSelected);

      if(editTextTitle.getText().toString().trim().equals(EMPTY)) {
         editTextTitle.setError(ERROR_MESSAGE_EMPTY);
         result = false;
      }

      if (!editTextPhone.getText().toString().trim().equals(EMPTY)){
         if (!Arrays.asList(contactLoader.getContactNames()).contains(editTextPhone.getText().toString())){
            editTextPhone.setError(ERROR_MESSAGE_INVALID_CONTACT);
            result = false;
         } else {
            this.contactModels.add(contactLoader.getContactFromName(editTextPhone.getText().toString()));
         }
      } else {
         editTextPhone.setError(ERROR_MESSAGE_EMPTY);
         result = false;
      }

      if(editTextMessage.getText().toString().trim().equals(EMPTY)){
         editTextMessage.setError(ERROR_MESSAGE_EMPTY);
         result = false;
      }

      if(textViewDate.getText().toString().equals(NO_DATE_SELECTED)){
         textViewDate.setText(ERROR_MESSAGE_NO_DATE_SELECTED);
         textViewDate.setTextColor(activity.getResources().getColor(com.google.android.material.R.color.design_default_color_error));
         result = false;
      }

      if(textViewTime.getText().toString().equals(NO_TIME_SELECTED)){
         textViewTime.setText(ERROR_MESSAGE_NO_TIME_SELECTED);
         textViewTime.setTextColor(activity.getResources().getColor(com.google.android.material.R.color.design_default_color_error));
         result = false;
      }
      return result;
   }
}
