package com.example.covid19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedbackpage extends AppCompatActivity {

    EditText nameField, emailField, feedbackField;
    Spinner feedbackSpinner;
    Button btn;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference("Feedback");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackpage);
        progressDialog = new ProgressDialog(this);
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Loading");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                FeedBackModel feedBackModel = new FeedBackModel(nameField.getText().toString(), emailField.getText().toString(), feedbackField.getText().toString(), feedbackSpinner.getSelectedItem().toString());
                String id = mDatabaseReference.push().getKey();
                mDatabaseReference.child(id).setValue(feedBackModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            String to = "tashgill14@gmail.com";
                            String subject = feedbackSpinner.getSelectedItem().toString();
                            String message = feedbackField.getText().toString();
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);
                            email.setType("message/rfc822");
                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                    }
                });
            }
        });
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getText(R.string.feedback));

        String[] containts = {"Content Related", "Complains regarding Covid-19", "Government Related", "Help!! Related"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_feedbackpage, containts);

        nameField = findViewById(R.id.etName);
        final String name = nameField.getText().toString();
        emailField = findViewById(R.id.etEmail);
        feedbackField = findViewById(R.id.etFeedback);
        feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);


        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();
        btn = findViewById(R.id.btnFeedBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
