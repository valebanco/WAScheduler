package it.bancon.wascheduler.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactLoader {
   Activity activity;
   Context context;
   ArrayList<ContactModel> contacts;

   public ContactLoader(Activity activity, Context context) {
      this.context = context;
      this.activity = activity;
      contacts = new ArrayList<>();
   }

   public String[] getContactNames() {
      ArrayList<String> names = new ArrayList<>();

      for (ContactModel el :contacts){
         names.add(el.getName());
      }

      String [] namesToAdapter = new String[names.size()];
      names.toArray(namesToAdapter);

      return namesToAdapter;
   }

   public List<ContactModel> getContactList (){
      return contacts;
   }
   public ContactModel getContactFromName(String name){
      ContactModel contactToRetrieve = new ContactModel();

      for (ContactModel el : contacts){
         if(el.getName().equals(name)){
            contactToRetrieve.setName(el.getName());
            contactToRetrieve.setNumber(el.getNumber());
         }
      }

      return contactToRetrieve;
   }

   public void loadWAContactList() {
      ContentResolver cr = context.getContentResolver();

      //RowContacts for filter Account Types
      Cursor contactCursor = cr.query(
              ContactsContract.RawContacts.CONTENT_URI,
              new String[]{ContactsContract.RawContacts._ID,
                      ContactsContract.RawContacts.CONTACT_ID},
              ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
              new String[]{"com.whatsapp"},
              null);

      if (contactCursor != null) {
         if (contactCursor.getCount() > 0) {
            if (contactCursor.moveToFirst()) {
               do {
                  //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                  @SuppressLint("Range") String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                  if (whatsappContactId != null) {
                     //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                     Cursor whatsAppContactCursor = cr.query(
                             ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                             new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                     ContactsContract.CommonDataKinds.Phone.NUMBER,
                                     ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                             ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                             new String[]{whatsappContactId}, null);

                     if (whatsAppContactCursor != null) {
                        whatsAppContactCursor.moveToFirst();
                        @SuppressLint("Range") String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        @SuppressLint("Range") String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        @SuppressLint("Range") String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        whatsAppContactCursor.close();

                        ContactModel model = new ContactModel();
                        model.setId(id);
                        model.setName(name);
                        model.setNumber(number);

                        contacts.add(model);

                        System.out.println( " WhatsApp contact id  :  " + id);
                        System.out.println( " WhatsApp contact name :  " + name);
                        System.out.println( " WhatsApp contact number :  " + number);
                     }
                  }
               } while (contactCursor.moveToNext());
               contactCursor.close();
            }
         }
      }

      System.out.println(" WhatsApp contact size :  " + contacts.size());
   }
   public void loadContactList() {
      Uri uri = ContactsContract.Contacts.CONTENT_URI;
      String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

      Cursor cursor = activity.getContentResolver().query(
              uri,null,null,null,sort
      );

      if(cursor.getCount() > 0){
         while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";

            Cursor phoneCursor = activity.getContentResolver().query(
                    uriPhone,null,selection,new String[]{id},null
            );

            if (phoneCursor.moveToNext()){
               @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

               ContactModel model = new ContactModel();
               model.setName(name);
               model.setNumber(number);

               contacts.add(model);

               phoneCursor.close();
            }
         }
      }
      cursor.close();
   }

   public ArrayList<ContactModel> getContacts(){
      return this.contacts;
   }
}

