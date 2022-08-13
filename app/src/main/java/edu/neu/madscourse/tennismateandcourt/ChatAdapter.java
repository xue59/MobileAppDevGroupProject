package edu.neu.madscourse.tennismateandcourt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int TYPE_SEND_TEXT=1;
    public static final int TYPE_RECEIVE_TEXT=2;
    Activity context;
    List<Message> dataList;
    public ChatAdapter(Activity context, List<Message> data) {
        this.context = context;
        this.dataList = data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = null;
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_SEND_TEXT:
                inflate = LayoutInflater.from(context).inflate(R.layout.item_text_send, parent, false);
                viewHolder = new ViewHolder(inflate);
                break;
            case TYPE_RECEIVE_TEXT:
                inflate = LayoutInflater.from(context).inflate(R.layout.item_text_receive, parent, false);
                viewHolder = new ViewHolder(inflate);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvContent.setText(dataList.get(position).getMsg());
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getSendUid().equals(FirebaseAuth.getInstance().getUid())){
            return TYPE_SEND_TEXT;
        }else{
            return TYPE_RECEIVE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public String formatTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(new Date(time));
    }

}
