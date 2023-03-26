package it.bancon.wascheduler.validator;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.AccessibleObject;
import java.util.List;

import it.bancon.wascheduler.R;
import it.bancon.wascheduler.model.Schedulation;
import it.bancon.wascheduler.model.SchedulationDetails;

public class ScheduleAddingValidator implements Validator{
   List<SchedulationDetails> schedulationDetailsList;
   SchedulationDetails schedulationToBeAdded;
   Context context;
   Activity activity;

   public ScheduleAddingValidator(Context context,Activity activity,List<SchedulationDetails> schedulationDetailsList,SchedulationDetails schedulationToBeAdded){
      this.schedulationDetailsList = schedulationDetailsList;
      this.schedulationToBeAdded = schedulationToBeAdded;
      this.context = context;
      this.activity = activity;
   }
   @Override
   public boolean validate() {

      boolean resultValidation = schedulationDetailsList.contains(schedulationToBeAdded);
      System.out.println("-----SCHEDULATION TO BE ADDED-----");
      System.out.println(schedulationToBeAdded);

      System.out.println("-----SCHEDULATION LIST-----");
      for(SchedulationDetails schedulationDetails: schedulationDetailsList){
         System.out.println(schedulationDetails);
      }

      if (resultValidation) {
         Toast.makeText(context,activity.getResources().getString(R.string.schedulation_already_present),Toast.LENGTH_SHORT).show();
      }
      return resultValidation;
   }
}
