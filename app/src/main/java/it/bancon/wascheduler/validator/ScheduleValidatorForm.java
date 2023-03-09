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
      TextView textViewCountContacts = activity.findViewById(R.id.textViewShowNumberAddedContacts);
      TextView textViewDate = activity.findViewById(R.id.textViewShowDateSelected);
      TextView textViewTime = activity.findViewById(R.id.textViewShowTimeSelected);

      if(editTextTitle.getText().toString().trim().equals(EMPTY)) {
         editTextTitle.setError(activity.getResources().getString(R.string.error_message_empty));
         result = false;
      }

      if(contactModels.isEmpty()){
         textViewCountContacts.setError(activity.getResources().getString(R.string.no_contacts_selected));
         result = false;
      } else {
         textViewCountContacts.setError(null,null);
      }

      if(editTextMessage.getText().toString().trim().equals(EMPTY)){
         editTextMessage.setError(activity.getResources().getString(R.string.error_message_empty));
         result = false;
      }

      if(textViewDate.getText().toString().equals(activity.getResources().getString(R.string.no_date_selected))){
         textViewDate.setText(activity.getResources().getString(R.string.error_message_no_date_selected));
         textViewDate.setTextColor(activity.getResources().getColor(com.google.android.material.R.color.design_default_color_error));
         result = false;
      }

      if(textViewTime.getText().toString().equals(activity.getResources().getString(R.string.no_time_selected))){
         textViewTime.setText(activity.getResources().getString(R.string.error_message_no_time_selected));
         textViewTime.setTextColor(activity.getResources().getColor(com.google.android.material.R.color.design_default_color_error));
         result = false;
      }
      return result;
   }
}
