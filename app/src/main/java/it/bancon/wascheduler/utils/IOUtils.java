package it.bancon.wascheduler.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetails;

public class IOUtils {

   public static boolean isEmptyFile(Context context,String fileName){
      File file = new File(context.getFilesDir(),fileName);
      boolean isEmpty = false;

      if(file.length() == 0){
         isEmpty = true;
      }

      return isEmpty;
   }

   public static List<SchedulationDetails> loadScheduleProgramsFromFile (Context context, String fileName) throws IOException {
      List<SchedulationDetails> schedulationDetails = new ArrayList<>();
      List<ContactModel> listContact = new ArrayList<>();

      File file = new File (context.getFilesDir(),fileName);
      BufferedReader br = new BufferedReader(new FileReader(file));

      ContactModel curContact;
      String line;
      String [] data;
      String [] contact;
      SchedulationDetails item;

      while((line = br.readLine()) != null) {
         item = new SchedulationDetails();
         data = line.split(AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS);

         item.setTitle(data[0]);
         item.setDescription(data[1]);
         item.setMessage(data[2]);
         item.setDateToSchedule(data[3]);
         item.setHourToSchedule(data[4]);

         for(int i = 5 ; i < data.length; i++){
            contact = data[i].split(AppContractClass.REGEX_ITEM_CONTACTS);
            curContact = new ContactModel();
            curContact.setName(contact[0]);
            curContact.setNumber(contact[1]);
            listContact.add(curContact);
         }

         item.setContacts(listContact);
         schedulationDetails.add(item);
      }

      br.close();
      return schedulationDetails;
   }
   public static void addScheduleProgramToFile (Context context, String fileName, SchedulationDetails schedulationDetails ) throws IOException {
      FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_APPEND);

      OutputStreamWriter osw = new OutputStreamWriter(fos);
      osw.write(schedulationDetails.toString());
      osw.flush();
      osw.close();

      fos.close();

   }

}
