package com.mavedev.profileutils.contacts.export;

import com.facebook.model.GraphUser;

import java.io.Serializable;

/**
 * Created by maverick on 29/4/14.
 */
public class Friend implements Serializable{

    String name;
    String firstName;
    String lastName;
    String middleName;
    String username;
    String id;
    String location;
    String birthday;
    String gender;
    String email;
    String link;

    public Friend() {
    }

    public Friend(GraphUser user){
        ExtendedGraphUser extendedGraphUser = new ExtendedGraphUser(user);
        this.name = extendedGraphUser.getUser().getName();
        this.firstName = extendedGraphUser.getUser().getFirstName();
        this.lastName = extendedGraphUser.getUser().getLastName();
        this.middleName = extendedGraphUser.getUser().getMiddleName();
        this.username = extendedGraphUser.getUser().getUsername();
        this.id = extendedGraphUser.getUser().getId();
        this.location = extendedGraphUser.getCity();
        this.birthday = extendedGraphUser.getUser().getBirthday();
        this.gender = extendedGraphUser.getGender();
        this.email = extendedGraphUser.getEmail();
        this.link = extendedGraphUser.getUser().getLink();
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
