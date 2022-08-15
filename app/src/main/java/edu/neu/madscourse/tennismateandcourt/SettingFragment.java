package edu.neu.madscourse.tennismateandcourt;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.Calendar;

public class SettingFragment extends Fragment {

    private static final String TAG = "ProfileActivity";
    private DatePickerDialog datePickerDialog;
    private TextInputEditText nameInput;
    private TextInputEditText emailInput;
    private TextInputEditText dateOfBirthInput;
    private AutoCompleteTextView genderACTV;
    private AutoCompleteTextView NTRPRatingACTV;
    private Button saveBtn;
    private Button logOutBtn;
    private DatabaseReference userRef;
    private FirebaseUser user;
    private String gender;
    private String dateOfBirth;
    private String ntrprating;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        nameInput = getView().findViewById(R.id.name_et);
        emailInput = getView().findViewById(R.id.email_address_et);
        dateOfBirthInput = getView().findViewById(R.id.date_of_birth_et);
        genderACTV = getView().findViewById(R.id.gender_actv);
        NTRPRatingACTV = getView().findViewById(R.id.NTRPRating_actv);
        saveBtn = getView().findViewById(R.id.save_btn);
        logOutBtn = getView().findViewById(R.id.logout_btn);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        init();
        getUserProfile();
    }

    private void getUserProfile() {
        String uid = user.getUid();
        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                displayUserProfile(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayUserProfile(User user) {
        nameInput.setText(user.getName());
        emailInput.setText(user.getEmail());
        dateOfBirthInput.setText(user.getDateOfBirth());
        genderACTV.setText(user.getGender(), false);
        NTRPRatingACTV.setText(user.getNtrprating(), false);
        gender = user.getGender();
        ntrprating = user.getNtrprating();
    }

    private void init() {
        ArrayAdapter<CharSequence> genderItemAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, R.layout.dropdown_item);
        genderACTV.setAdapter(genderItemAdapter);
        genderACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }
        });

        ArrayAdapter<CharSequence> NTRPRatingItemAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.NTRPRating, R.layout.dropdown_item);
        NTRPRatingACTV.setAdapter(NTRPRatingItemAdapter);
        NTRPRatingACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ntrprating = parent.getItemAtPosition(position).toString();
            }
        });

        dateOfBirthInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

    private void updateUserProfile() {
        String uid = user.getUid();
        userRef.child(uid).child("name").setValue(nameInput.getText().toString());
        userRef.child(uid).child("email").setValue(emailInput.getText().toString());
        userRef.child(uid).child("dateOfBirth").setValue(dateOfBirthInput.getText().toString());
        userRef.child(uid).child("gender").setValue(gender);
        userRef.child(uid).child("ntrprating").setValue(ntrprating);
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
        datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private String makeDateString(int year, int month, int day) {
        return month + "/" + day + "/" + year;
    }


    private void signOut() {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        restart();
                    }
                });
    }

    public void restart(){
        ProcessPhoenix.triggerRebirth(getActivity());
    }
}