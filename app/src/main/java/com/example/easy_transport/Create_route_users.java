package com.example.easy_transport;

public class Create_route_users {

    String from,dest,date,vehicle,space,contact,routeid;

    public Create_route_users(String from, String dest, String date, String vehicle, String space, String contact, String routeid) {
        this.from = from;
        this.dest = dest;
        this.date = date;
        this.vehicle = vehicle;
        this.space = space;
        this.contact = contact;
        this.routeid = routeid;
    }

    public Create_route_users(){}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }
}
