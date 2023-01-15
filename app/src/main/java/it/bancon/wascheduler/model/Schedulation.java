package it.bancon.wascheduler.model;

import java.util.List;

public abstract class Schedulation {
    protected String title;
    protected String description;
    protected List<ContactModel> contacts;
    protected String dateToSchedule;
    protected String hourToSchedule;

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
        return hourToSchedule;
    }

    public void setHourToSchedule(String hourToSchedule) {
        this.hourToSchedule = hourToSchedule;
    }
}
