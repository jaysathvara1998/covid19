package com.example.covid19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    EditText emailId, password, confPassword, fName, lName, address, city, country;
    CheckBox cbPrivacyStatus;
    Button btnSignUp, btnSignIn;
    ProgressDialog progressDialog1;
    private FirebaseAuth mAuth;
    String strEmail, strPassword, strConfPassword, stStatus, strFName, strLName, strAddress, strCity, strCountry, strCoronaStatus, isHide;
    Spinner coronaStatus;

    String[] status = {"Positive", "Negative"};

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        listener();
    }

    private void init() {

        emailId = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confPassword = findViewById(R.id.etConfPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        coronaStatus = findViewById(R.id.spCoronaStatus);
        fName = findViewById(R.id.etFirstName);
        lName = findViewById(R.id.etLastName);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        cbPrivacyStatus = findViewById(R.id.cbHide);
        coronaStatus.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        coronaStatus.setAdapter(aa);
        mAuth = FirebaseAuth.getInstance();
    }

    private void listener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intosignin = new Intent(register.this, MainActivity.class);
                startActivity(intosignin);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = emailId.getText().toString();
                strFName = fName.getText().toString();
                strLName = lName.getText().toString();
                strAddress = address.getText().toString();
                strCity = city.getText().toString();
                strCountry = country.getText().toString();
                strPassword = password.getText().toString();
                strConfPassword = confPassword.getText().toString();
                strCoronaStatus = coronaStatus.getSelectedItem().toString();
                progressDialog1 = new ProgressDialog(register.this);
                if (cbPrivacyStatus.isChecked()) {
                    isHide = "1";
                } else {
                    isHide = "0";
                }
                Toast.makeText(register.this, isHide, Toast.LENGTH_SHORT).show();
                progressDialog1.setIcon(R.drawable.icon2);
                progressDialog1.setTitle("Loading");
                progressDialog1.setMessage("please wait, Don't panic sometimes it take a while!!");

                if (validation()) {
                    progressDialog1.show();
                    mAuth.createUserWithEmailAndPassword(strEmail, strConfPassword).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog1.hide();
                            if (task.isSuccessful()) {
                                UserModel user = new UserModel(strFName, strLName, strEmail, strPassword, strCoronaStatus, strAddress, strCity, strCountry, isHide);
                                DatabaseReference usersRef = mDatabaseReference.child(task.getResult().getUser().getUid());
                                usersRef.setValue(user);
                                Intent intohome = new Intent(register.this, Navegation.class);
                                startActivity(intohome);
                            } else {
                                Toast.makeText(register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validation() {
        if (strFName.isEmpty()) {
            fName.setError("This field is required");
            fName.requestFocus();
            return false;
        }
        if (strLName.isEmpty()) {
            lName.setError("This field is required");
            lName.requestFocus();
            return false;
        }
        if (strAddress.isEmpty()) {
            address.setError("This field is required");
            address.requestFocus();
            return false;
        }
        if (strCity.isEmpty()) {
            city.setError("This field is required");
            city.requestFocus();
            return false;
        }
        if (strCountry.isEmpty()) {
            country.setError("This field is required");
            country.requestFocus();
            return false;
        }
        if (strEmail.isEmpty()) {
            emailId.setError("Email not entered");
            emailId.requestFocus();
            return false;
        }
        if (strPassword.isEmpty() && strConfPassword.isEmpty()) {
            password.setError("password not entered");
            password.requestFocus();
            return false;
        }
        if (!(strPassword.equals(strConfPassword))) {
            confPassword.setError("password not matched");
            confPassword.requestFocus();
            return false;
        }
        if (strPassword.length() <= 8) {
            password.setError("password should be more than 8 char");
            password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stStatus = status[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
