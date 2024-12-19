package com.example.wmpfinalexam;

import java.io.Serializable;

public class Subjects implements Serializable {
    private String name;
    private int credits;

    public Subjects() {
        // Default constructor required for calls to DataSnapshot.getValue(Subject.class)
    }

    public Subjects(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}
