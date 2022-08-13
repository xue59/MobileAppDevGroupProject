package edu.neu.madscourse.tennismateandcourt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class UserListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwRefresh;
    private List<User> list = new ArrayList<>();
    private UserAdapter mAdapter;
    private DatabaseReference databaseReference;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText etName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mProgressDialog =  new ProgressDialog(this);
        initView();
        init();
    }

    public List<String> getMyFriends() {
        return myInfo.getFriends();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                if (TextUtils.isEmpty(name)){
                    return;
                }
                search(name);
            }
        });
        mRecyclerView = findViewById(R.id.recyclerView);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UserAdapter( this, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemChatClick(int position) {
                Intent intent = new Intent(UserListActivity.this,ChatActivity.class);
                intent.putExtra("user",list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemAddClick(int position) {
                addFriend(list.get(position).getUid());
            }


        });
    }

    private void search(String name) {
        mProgressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                list.clear();
                ArrayList<User> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User userInfo = snapshot.getValue(User.class);
                    userInfo.setUid(snapshot.getKey());
                    if (!userInfo.getUid().equals(currentUser.getUid())){
                        if (userInfo.getUid().equals(name)||userInfo.getName().contains(name)
                        ||userInfo.getEmail().contains(name)){
                            tempList.add(userInfo);
                        }
                    }
                }
                for (int i = 0; i < tempList.size(); i++) {
                    User temp = tempList.get(i);
                    Double dis = getDistance(myInfo.getLon(), myInfo.getLat(), temp.getLon(), temp.getLat());
                    temp.setDistance(dis);
                }
                tempList.sort(new Comparator<User>() {
                    @Override
                    public int compare(User userInfo, User t1) {
                        return (int) (userInfo.getDistance()-t1.getDistance());
                    }
                });
                list.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                mSwRefresh.setRefreshing(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void addFriend(String uid) {
        DatabaseReference myDf = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        myDf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myInfo = snapshot.getValue(User.class);
                if (myInfo.getFriends()==null){
                    List<String> friends = new ArrayList<>();
                    friends.add(uid);
                    myInfo.setFriends(friends);
                }else{
                    List<String> friends = myInfo.getFriends();
                    friends.add(uid);
                    myInfo.setFriends(friends);
                }
                myDf.setValue(myInfo);
                init();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private User myInfo;

    private void init() {
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                list.clear();
                ArrayList<User> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User userInfo = snapshot.getValue(User.class);
                    userInfo.setUid(snapshot.getKey());
                    if (userInfo.getUid().equals(currentUser.getUid())){
                        myInfo = userInfo;
                    }else {
                        tempList.add(userInfo);
                    }
                }
                for (int i = 0; i < tempList.size(); i++) {
                    User temp = tempList.get(i);
                    Double dis = getDistance(myInfo.getLon(), myInfo.getLat(), temp.getLon(), temp.getLat());
                    temp.setDistance(dis);
                }
                tempList.sort(new Comparator<User>() {
                    @Override
                    public int compare(User userInfo, User t1) {
                        return (int) (userInfo.getDistance()-t1.getDistance());
                    }
                });
                list.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                mSwRefresh.setRefreshing(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });

    }
    private static final double EARTH_RADIUS = 6378.137;
    public Double getDistance(double longitude1, double latitude1,
                              double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        s = Math.round(s / 100d) / 10d;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
