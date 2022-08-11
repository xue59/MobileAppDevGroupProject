package edu.neu.madscourse.tennismateandcourt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Photo_rv_adapter extends RecyclerView.Adapter<Photo_rv_adapter.holder> {
    ArrayList<String> photoList;
    Context context;
    public Photo_rv_adapter(Context context, ArrayList<String> photoList){
        this.photoList=photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.a_court_photo_holder, parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class holder extends RecyclerView.ViewHolder{
        public holder(@NonNull View itemView){
            super(itemView);
        }
    }
}
