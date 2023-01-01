package it.bancon.wascheduler.model;

import it.bancon.wascheduler.AppContractClass;

public class SchedulationDetails extends Schedulation{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
}
