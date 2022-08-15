package edu.neu.madscourse.tennismateandcourt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Photo_rv_adapter extends RecyclerView.Adapter<Photo_rv_adapter.holder> {
    List<String> photoList;
    Context context;
    public Photo_rv_adapter(Context context, List<String> photoList){
        this.photoList=photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //临时先展示系统自带的 logo 图片作为球场照片
        View view = LayoutInflater.from(this.context).inflate(R.layout.a_court_photo_holder, parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Log.d("onbind view display image: ", photoList.get(0).toString());
        if (photoList.size() != 0) {
            Glide.with(this.context)
                    .load(photoList.get(position).toString()) // image url
                    .placeholder(R.drawable.a_tennis_court) // any placeholder to load at start
                    .error(R.drawable.tennisappicon)  // any image in case of error
//                .override(180, 180) // resizing
//                .centerCrop()
                    .into(holder.img_viewholder);  // imageview object
        }
        else{
            Glide.with(this.context)
                    .load(R.drawable.tennisappicon) // image url
                    .placeholder(R.drawable.a_tennis_court) // any placeholder to load at start
                    .error(R.drawable.tennisappicon)  // any image in case of error
//                .override(180, 180) // resizing
//                .centerCrop()
                    .into(holder.img_viewholder);  // imageview object
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class holder extends RecyclerView.ViewHolder{
        ImageView img_viewholder;
        public holder(@NonNull View itemView){
            super(itemView);
            img_viewholder = itemView.findViewById(R.id.img_viewholder);
        }

    }
}
