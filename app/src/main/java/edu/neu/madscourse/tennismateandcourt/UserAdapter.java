package edu.neu.madscourse.tennismateandcourt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> notices ;
    UserListFragment context ;
    public UserAdapter(UserListFragment context, List<User> notices){
        this.notices = notices;
        this.context = context;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(context.getActivity()).inflate(R.layout.item_users,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User userInfo = notices.get(position);
        holder.tv_name.setText(userInfo.getEmail());
        holder.tv_location.setText(userInfo.getDistance()+"km");
        if (context.getMyFriends().contains(userInfo.getUid())){
            holder.iv_add.setImageResource(R.drawable.love);
        }else{
            holder.iv_add.setImageResource(R.drawable.friends_add);
        }
        holder.iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemChatClick(position);
                }
            }
        });
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemAddClick(position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return notices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_location;
        ImageView iv_chat;
        ImageView iv_add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            iv_chat = itemView.findViewById(R.id.iv_chat);
            iv_add = itemView.findViewById(R.id.iv_add);

        }
    }
    private OnItemClickListener onItemClickListener;
    interface OnItemClickListener{
        void onItemChatClick(int position);
        void onItemAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
