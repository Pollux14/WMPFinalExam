package com.example.wmpfinalexam;

import java.util.List;

public class EnrollmentData {
    private List<Subjects> subjects;
    private int totalCredits;

    public EnrollmentData(List<Subjects> subjects, int totalCredits) {
        this.subjects = subjects;
        this.totalCredits = totalCredits;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public int getTotalCredits() {
        return totalCredits;
    }
}
