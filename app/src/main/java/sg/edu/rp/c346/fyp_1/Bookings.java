package sg.edu.rp.c346.fyp_1;

import java.io.Serializable;

public class Bookings implements Serializable {
    private String service;
    private String date;
    private String time;
    private String street;
    private String postal;
    private String notes;
    private String contact;
    private String email;
    private String cleaner;
    private String cost;
    private String payment;

    public Bookings() {
    }

    public Bookings(String service, String date, String time, String street, String postal, String notes, String contact, String email, String cleaner, String cost, String payment) {
        this.service = service;
        this.date = date;
        this.time = time;
        this.street = street;
        this.postal = postal;
        this.notes = notes;
        this.contact = contact;
        this.email = email;
        this.cleaner = cleaner;
        this.cost = cost;
        this.payment = payment;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
