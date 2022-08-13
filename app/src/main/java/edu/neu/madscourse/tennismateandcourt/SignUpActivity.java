package edu.neu.madscourse.tennismateandcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private DatePickerDialog datePickerDialog;
    private TextInputEditText nameInput;
    private TextInputEditText emailInput;
    private TextInputEditText dateOfBirthInput;
    private AutoCompleteTextView genderACTV;
    private AutoCompleteTextView NTRPRatingACTV;
    private FusedLocationProviderClient client;
    private final static int REQUEST_CODE = 100;
    private Button nextBtn;
    private String gender;
    private String dateOfBirth;
    private String NTRPRating;
    private Double lat;
    private Double lon;
    private DatabaseReference userRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameInput = findViewById(R.id.name_et);
        emailInput = findViewById(R.id.email_address_et);
        dateOfBirthInput = findViewById(R.id.date_of_birth_et);
        genderACTV = findViewById(R.id.gender_actv);
        NTRPRatingACTV = findViewById(R.id.NTRPRating_actv);
        nextBtn = findViewById(R.id.continue_btn);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        if (user.getEmail() != null) {
            emailInput.setText(user.getEmail());
        }

        if (user.getDisplayName() != null) {
            nameInput.setText(user.getDisplayName());
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();
                User user = new User(nameInput.getText().toString(), emailInput.getText().toString(), dateOfBirth, gender, NTRPRating, lat, lon);
                userRef.child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        ArrayAdapter<CharSequence> genderItemAdapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.dropdown_item);
        genderACTV.setAdapter(genderItemAdapter);
        genderACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
        });

        ArrayAdapter<CharSequence> NTRPRatingItemAdapter = ArrayAdapter.createFromResource(this, R.array.NTRPRating, R.layout.dropdown_item);
        NTRPRatingACTV.setAdapter(NTRPRatingItemAdapter);
        NTRPRatingACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NTRPRating = parent.getItemAtPosition(position).toString();
            }
        });
        dateOfBirthInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        getCurrentLocation();

    }

    public void getCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            });
        } else {
            // When permission is denied
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(this,
                            "This app requires location permission in order to provide you with the best user experience",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    public void openDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                dateOfBirth = makeDateString(year, month, day);
                dateOfBirthInput.setText(dateOfBirth);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private String makeDateString(int year, int month, int day) {
        return month + "/" + day + "/" + year;
    }
}