package it.bancon.wascheduler.model;

import java.util.List;
import java.util.Objects;

public abstract class Schedulation {
    protected int id;
    protected String title;
    protected String description;
    protected List<ContactModel> contacts;
    protected String dateToSchedule;
    protected String hourToSchedule;


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedulation that = (Schedulation) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(contacts, that.contacts) && Objects.equals(dateToSchedule, that.dateToSchedule) && Objects.equals(hourToSchedule, that.hourToSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, contacts, dateToSchedule, hourToSchedule);
    }


}
