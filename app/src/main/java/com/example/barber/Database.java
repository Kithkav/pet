package com.example.barber;

import java.util.Date;

class User {
    private String name;
    private String mobile;
    private Pet pet = new Pet();

    public User(String name, String mobileNumber) {
        this.name = name;
        this.mobile = mobileNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getMobileNumber() {
        return this.mobile;
    }

    public Pet getPet() {
        return this.pet;
    }
}

class TodoTask {
    private String description;
    private boolean done;

    public TodoTask() {

    }

    public TodoTask(String description) {
        this.description = description;
        this.done = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }
}

class Appointment {
    private String description;
    private boolean done;
    private Date date;

    public Appointment() {

    }

    public Appointment(String description, Date date) {
        this.date = date;
        this.description = description;
        this.done = false;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }
}

class Pet {
    private String name = "";
    private String type = "";
    private Date birthDate = new Date();
    private boolean gender = true;
    private String color = "";

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean getGender() {
        return gender;
    }

    public String getColor() {
        return color;
    }
}
