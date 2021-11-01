package com.franzoo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="adv_prof")
public class Adv_prof {
@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
private long user_id;

@Column(nullable=true,length=25)
private String About_me;

@Column(nullable=true,length=25)
private String Interest;

@Column(nullable=true,length=20)
private String Hobbies;

@Column(nullable=true,length=20)
private String Activities;

@Column(nullable=true,length=20)
private String Location;



public long getUser_id() {
return user_id;
}



public void setUser_id(long user_id) {
this.user_id = user_id;
}


public String getAbout_me() {
return About_me;
}


public void setAbout_me(String about_me) {
About_me = about_me;
}


public String getInterest() {
return Interest;
}


public void setInterest(String interest) {
Interest = interest;
}



public String getHobbies() {
return Hobbies;
}



public void setHobbies(String hobbies) {
Hobbies = hobbies;
}

public String getActivities() {
return Activities;
}

public void setActivities(String activities) {
Activities = activities;
}

public String getLocation() {
return Location;
}

public void setLocation(String location) {
Location = location;
}

}