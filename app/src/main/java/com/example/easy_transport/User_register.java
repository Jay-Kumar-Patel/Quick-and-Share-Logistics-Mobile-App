package com.example.easy_transport;

public class User_register {

    String aboutCompany,companyname,contact,compayemail,address;

    public User_register(String aboutCompany, String companyname, String contact, String compayemail, String address) {
        this.aboutCompany = aboutCompany;
        this.companyname = companyname;
        this.contact = contact;
        this.compayemail = compayemail;
        this.address = address;
    }

    public User_register(){}

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCompayemail() {
        return compayemail;
    }

    public void setCompayemail(String compayemail) {
        this.compayemail = compayemail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
