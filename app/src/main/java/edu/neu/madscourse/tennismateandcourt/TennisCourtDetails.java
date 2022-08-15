package edu.neu.madscourse.tennismateandcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TennisCourtDetails extends AppCompatActivity {
    RecyclerView photo_rv, info_rv;
    ArrayList<String>  info_list;
    LinearLayoutManager linearLayoutManager;
    Photo_rv_adapter photo_rv_adapter;
    Info_rv_adapter info_rv_adapter;
    private Uri imageUri;
    private TennisCourtModel tennisCourtModel;
    List<String> photoSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis_court_details);
//        ActionBar actionBar = getSupportActionBar(); // calling the go back bar 暂时comment掉 action bar
//        actionBar.setDisplayHomeAsUpEnabled(true); // showing the go back bar暂时comment掉 action bar
        //starts coding here: ....

        Bundle data = getIntent().getExtras();
        tennisCourtModel = (TennisCourtModel) data.getParcelable("TennisCourtModel"); //get 传输过来的tennis court
        //code for display photo horizontal RV:
        photo_rv = findViewById(R.id.photos_recyclerview_horizontal);
        // Hardcode 一些dummy photo in the list
//        photoSource = new ArrayList<>();
        photoSource = tennisCourtModel.getPhotos();

        linearLayoutManager = new LinearLayoutManager(TennisCourtDetails.this,LinearLayoutManager.HORIZONTAL,false);
        photo_rv_adapter = new Photo_rv_adapter(TennisCourtDetails.this,photoSource);
        photo_rv.setLayoutManager(linearLayoutManager);
        photo_rv.setAdapter(photo_rv_adapter);


        // code for display court info details RV:

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

        findViewById(R.id.floatingActionButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0;
    private void checkData(){
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        if (permissionList.size() <= 0) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, permissions, TAKE_PHOTO_PERMISSION_REQUEST_CODE);
        }
    }

    private void openCamera() {
        File outImage = new File(getExternalCacheDir(), "image.jpg");
        if(outImage.exists()){
            outImage.delete();
        }
        try {
            outImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = FileProvider.getUriForFile(this, "edu.neu.madscourse.tennismateandcourt.fileProvider", outImage);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(openCameraIntent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case 100:
                    upload(imageUri);
                    break;

                default:
                    break;
            }
        }

    }
    private void upload(Uri selectedUri) {
        StorageReference mStoreReference = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStoreReference.child("imgs/"+System.currentTimeMillis()+".jpg");
        UploadTask uploadTask = riversRef.putFile(selectedUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imageUrl = downloadUri.toString();

                    updateImages(imageUrl);
                } else {

                }
            }
        });

    }

    private void updateImages(String imageUrl) {
        List<String> photos = tennisCourtModel.getPhotos();
        if (photos==null){
            photos = new ArrayList<>();
        }
        photos.add(imageUrl);
        tennisCourtModel.setPhotos(photos);
        DatabaseReference myDf = FirebaseDatabase.getInstance().getReference().child("Courts")
                .child(tennisCourtModel.getKey());
        myDf.setValue(tennisCourtModel);
    }

    //Back arrow button 后退按键
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}