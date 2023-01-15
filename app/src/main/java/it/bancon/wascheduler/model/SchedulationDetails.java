package it.bancon.wascheduler.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

import it.bancon.wascheduler.configuration.AppContractClass;

public class SchedulationDetails extends Schedulation implements Parcelable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SchedulationDetails(){};

    @Override
    public String toString() {
        String resultString =  super.getTitle() + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS
                + super.getDescription() + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS
                + message + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS
                + super.getDateToSchedule() + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS
                + super.getHourToSchedule() + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS;

        for (ContactModel contact : super.getContacts()){
            resultString = resultString + contact.getName() + AppContractClass.REGEX_ITEM_CONTACTS + contact.getNumber() + AppContractClass.REGEX_ITEM_SCHEDULATION_DETAILS;
        }
        resultString = resultString + "\n";

        return resultString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int c) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(message);
        parcel.writeString(dateToSchedule);
        parcel.writeString(hourToSchedule);
        ContactModel arrayContactModel[] = ContactModel.CREATOR.newArray(contacts.size());

        for(int i = 0; i < contacts.size() ; i++) {
            arrayContactModel[i] = contacts.get(i);
        }

        parcel.writeArray(arrayContactModel);
    }
    public static final Parcelable.Creator<SchedulationDetails> CREATOR
            = new Parcelable.Creator<SchedulationDetails>() {
        public SchedulationDetails createFromParcel(Parcel in) {
            return new SchedulationDetails(in);
        }

        public SchedulationDetails[] newArray(int size) {
            return new SchedulationDetails[size];
        }
    };

    public SchedulationDetails(Parcel in) {
        title = in.readString();
        description = in.readString();
        message = in.readString();
        dateToSchedule = in.readString();
        hourToSchedule = in.readString();

        Object[] objectsFromReading = in.readArray(ContactModel.class.getClassLoader());
        contacts = new ArrayList<>(objectsFromReading.length);

        for (int i =0; i < objectsFromReading.length-1; i++){
            contacts.add((ContactModel) objectsFromReading[i]);
        }
    }
}
