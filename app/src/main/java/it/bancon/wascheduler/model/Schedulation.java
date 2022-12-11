package it.bancon.wascheduler.model;

import java.util.List;

public abstract class Schedulation {
    private String title;
    private String description;
    private List<ContactModel> contacts;
    private String dateToSchedule;
    private String HourToSchedule;

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateToSchedule() {
        return dateToSchedule;
    }

    public void setDateToSchedule(String dateToSchedule) {
        this.dateToSchedule = dateToSchedule;
    }

    public String getHourToSchedule() {
        return HourToSchedule;
    }

    public void setHourToSchedule(String hourToSchedule) {
        HourToSchedule = hourToSchedule;
    }
}
