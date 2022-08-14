package edu.neu.madscourse.tennismateandcourt;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference databaseReference1;
    private List<Message> list = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ChatAdapter mAdapter;
    private RecyclerView rvChat;
    private EditText etContent;
    private ProgressDialog dialog;
    public static String sendUid ="";
    public static String toUid ="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        dialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = (User) getIntent().getSerializableExtra("user");
        toUid = user.getUid();
        rvChat =  findViewById(R.id.rv_chat);
        etContent =  findViewById(R.id.et_content);
        mAdapter = new ChatAdapter(this, list);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        rvChat.setLayoutManager(mLinearLayout);
        rvChat.setAdapter(mAdapter);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(user.getEmail());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.nav_left);
        toolbar.setNavigationOnClickListener(v -> finish());
        firebaseAuth = FirebaseAuth.getInstance();
        sendUid = firebaseAuth.getUid();

        getData(sendUid,user.getUid());
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)){
                    return;
                }
                sendText(content);
            }
        });
    }





    private void getData(final String from_id, final String to_id ){

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message chat = snapshot.getValue(Message.class);
                    if(chat.getSendUid().equals(from_id) && chat.getToUid().equals(to_id) ||
                            chat.getSendUid().equals(to_id) && chat.getToUid().equals(from_id)){
                        list.add(chat);
                    }


                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendText(String msg) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sendUid", sendUid);
        hashMap.put("msg", msg);
        hashMap.put("toUid", toUid);
        hashMap.put("timestamp", System.currentTimeMillis());
        databaseReference.child("Chats").push().setValue(hashMap);
        etContent.setText("");

        hideKeyboard(etContent);
    }
    public  void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
