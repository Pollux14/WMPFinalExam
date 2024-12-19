package com.example.wmpfinalexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSummaryPage extends AppCompatActivity {

  private LinearLayout subjectListLayout;
    private TextView TextViewTotCreds;
    private Button BackButton;
    private FirebaseFirestore db;
    private String enrollmentDocumentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary_page); // Set the content view

        subjectListLayout = findViewById(R.id.subjectListLayout);
        TextViewTotCreds = findViewById(R.id.TextViewTotCreds);
        BackButton = findViewById(R.id.BackButton);
        db = FirebaseFirestore.getInstance();

        // Get the data passed from Enrollment activity
        Intent intent = getIntent();
        Subjects[] selectedSubjectsArray = (Subjects[]) intent.getSerializableExtra("selectedSubjects");
        int totalCredits = intent.getIntExtra("totalCredits", 0);
        enrollmentDocumentId = intent.getStringExtra("enrollmentDocumentId"); // Get the document ID

        if (selectedSubjectsArray != null) {
            List<Subjects> selectedSubjects = new ArrayList<>(List.of(selectedSubjectsArray));
            // Display the subjects and total credits
            for (Subjects subject : selectedSubjects) {
                TextView subjectTextView = new TextView(this);
                subjectTextView.setText(subject.getName() + " (" + subject.getCredits() + " credits)");
                subjectListLayout.addView(subjectTextView);
            }
            TextViewTotCreds.setText("Total Credits: " + totalCredits);
        } else {
            TextViewTotCreds.setText("Total Credits: 0");
        }

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropEnrollment();
            }
        });
    }

    private void dropEnrollment() {
        if (enrollmentDocumentId != null) {
            DocumentReference enrollmentRef = db.collection("Enrollments").document(enrollmentDocumentId);
            enrollmentRef.delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EnrollmentSummaryPage.this, "Enrollment dropped successfully.", Toast.LENGTH_SHORT).show();
                        // Redirect to Enrollment activity
                        Intent intent = new Intent(EnrollmentSummaryPage.this, EnrollmentPage.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EnrollmentSummaryPage.this, "Error dropping enrollment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No enrollment to drop.", Toast.LENGTH_SHORT).show();
        }
    }

}
