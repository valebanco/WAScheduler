package it.bancon.wascheduler.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {
   private String id;
   private String name;
   private String number;

   public ContactModel() {

   }


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(id);
      parcel.writeString(name);
      parcel.writeString(number);
   }
   protected ContactModel(Parcel in) {
      id = in.readString();
      name = in.readString();
      number = in.readString();
   }

   public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
      @Override
      public ContactModel createFromParcel(Parcel in) {
         return new ContactModel(in);
      }

      @Override
      public ContactModel[] newArray(int size) {
         return new ContactModel[size];
      }
   };
}

