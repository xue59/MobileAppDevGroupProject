package edu.neu.madscourse.tennismateandcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private Button sendCodeToEmailBtn;
    private Button sendCodeToPhoneBtn;
    private TextView switchToPhoneTV;
    private TextView switchToEmailTV;
    private Button continueWithEmailBtn;
    private Button continueWithPhoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_with_email);

        sendCodeToEmailBtn = findViewById(R.id.sendCodeToEmail_bt);
        sendCodeToPhoneBtn = findViewById(R.id.sendCodeToPhone_bt);
        switchToEmailTV = findViewById(R.id.switch_to_email_tv);
        switchToPhoneTV = findViewById(R.id.switch_to_phone_tv);
        continueWithEmailBtn = findViewById(R.id.)


    }
}