package edu.neu.madscourse.tennismateandcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TennisCourtDetails extends AppCompatActivity {
    RecyclerView photo_rv, info_rv;
    ArrayList<String> photoSource, info_list;
    LinearLayoutManager linearLayoutManager;
    Photo_rv_adapter photo_rv_adapter;
    Info_rv_adapter info_rv_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis_court_details);
        ActionBar actionBar = getSupportActionBar(); // calling the go back bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar
        //starts coding here: ....

        //code for display photo horizontal RV:
        photo_rv = findViewById(R.id.photos_recyclerview_horizontal);
        // Hardcode 一些dummy photo in the list
        photoSource = new ArrayList<>();
        photoSource.add("1st");
        photoSource.add("2nd");
        photoSource.add("3nd");
        photoSource.add("4nd");
        photoSource.add("5nd");

        linearLayoutManager = new LinearLayoutManager(TennisCourtDetails.this,LinearLayoutManager.HORIZONTAL,false);
        photo_rv_adapter = new Photo_rv_adapter(TennisCourtDetails.this,photoSource);
        photo_rv.setLayoutManager(linearLayoutManager);
        photo_rv.setAdapter(photo_rv_adapter);


        // code for display court info details RV:
        Bundle data = getIntent().getExtras();
        TennisCourtModel tennisCourtModel = (TennisCourtModel) data.getParcelable("TennisCourtModel");
        Log.d("Test display court model transit: ", tennisCourtModel.getName());
        info_rv = findViewById(R.id.info_recyclerview);
        info_list = new ArrayList<>();
        info_list.add(tennisCourtModel.getId()+"");
        info_list.add(tennisCourtModel.getName());
        info_list.add(tennisCourtModel.getRating()+"");
        info_list.add(tennisCourtModel.getAddress());
        info_list.add(tennisCourtModel.getHoursOfOperations());
        info_list.add(tennisCourtModel.getWebsite());
        info_list.add(tennisCourtModel.getPhone());
        info_list.add(tennisCourtModel.getLastUpdateTime());
        linearLayoutManager = new LinearLayoutManager(TennisCourtDetails.this, LinearLayoutManager.VERTICAL,false);
        info_rv_adapter = new Info_rv_adapter(TennisCourtDetails.this, info_list);
        info_rv.setLayoutManager(linearLayoutManager);
        info_rv.setAdapter(info_rv_adapter);


    }




    //Back arrow button 后退按键
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}