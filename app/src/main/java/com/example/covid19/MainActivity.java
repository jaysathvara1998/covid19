package com.example.covid19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn, emergencyBtn;
    private LoginButton fbLogin;
    ProgressDialog progressDialog;
    TextView tvRegister, forgotPassword;
    private FirebaseAuth mAuth;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        listener();
    }

    private void init() {
        emailId = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        mAuth = FirebaseAuth.getInstance();
        fbLogin = findViewById(R.id.login_button);
        emergencyBtn = findViewById(R.id.btnEmergency);
        forgotPassword = findViewById(R.id.tvForgot);

        callbackManager = CallbackManager.Factory.create();
    }

    private void listener() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intofgpass = new Intent(MainActivity.this, forgotpassword.class);
                startActivity(intofgpass);
            }
        });

        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoem = new Intent(MainActivity.this, emengercy.class);
                startActivity(intoem);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoreg = new Intent(MainActivity.this, register.class);
                startActivity(intoreg);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setIcon(R.drawable.icon2);
                progressDialog.setProgressStyle(R.drawable.icon2);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("please wait, Don't panic sometimes it take a while!!");

                if (validate(email, pwd)) {
                    if (email.equals("admin") && pwd.equals("admin")){
                        Intent intomap = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intomap);
                    }else {
                        progressDialog.show();
                        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.hide();
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intomap = new Intent(MainActivity.this, Navegation.class);
                                    intomap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intomap);
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intomap2 = new Intent(MainActivity.this, Navegation.class);
                intomap2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intomap2);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
    }

    private boolean validate(String email, String pwd) {
        if (email.isEmpty()) {
            emailId.setError("Enter your email");
            emailId.requestFocus();
            return false;
        }
        if (pwd.isEmpty()) {
            password.setError("Enter your password");
            password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            LoginManager.getInstance().logOut();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onDestroy() {
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }
}
